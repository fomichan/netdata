package com.fomich.netdata.http.controller;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.entity.ModuleType;
import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.dto.*;
import com.fomich.netdata.service.ChannelService;
import com.fomich.netdata.service.MultiplexerService;
import com.fomich.netdata.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;
    private final SiteService siteService;
    private final MultiplexerService multiplexerService;



    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id,
                           Model model,
                           @RequestParam(value = "page", defaultValue = "1") int pageNumber,
                           MultiplexerFilter filter,
                           Pageable pageable
                           ) {

        Page<MultiplexerReadDto> page = multiplexerService.findAll(filter, pageable.withPage(pageNumber - 1));

        return channelService.findById(id)
                .map(channel -> {
                    model.addAttribute("multiplexers", PageResponse.of(page)); // чтобы можно было искать мультиплексоры для добавления по фильтру
                    model.addAttribute("filter", filter);
                    model.addAttribute("sites", siteService.findAllShort());

                    model.addAttribute("channel", channel);


                    return "channel/channel";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }



    // спринг может предоставить pageable, у него есть для этого специальный argument resolver
    // Для этого нужно передавать параметры с названиями page и size
    @GetMapping
    public String findAll(Model model,
                          @RequestParam(name = "direction", defaultValue = "asc") String direction,
                          @RequestParam(name = "sort", defaultValue = "name") String sort,
                          @RequestParam(value = "page", defaultValue = "1") int pageNumber, // будем брать отсюда, а не из pageable чтобы начинался с 1
                          ChannelFilter filter,
                          Pageable pageable) {

        // Создайте объект Sort на основе параметров сортировки
        Sort sortObj = Sort.by(Sort.Direction.fromString(direction), sort);
        // Создайте объект Pageable с учетом сортировки
        Pageable pageableWithSort = PageRequest.of(pageNumber - 1, pageable.getPageSize(), sortObj);

        Page<ChannelReadDto> page = channelService.findAll(filter, pageableWithSort);
        model.addAttribute("channels", PageResponse.of(page));
        model.addAttribute("sites", siteService.findAllShort());
        model.addAttribute("filter", filter);
        return "channel/channels";
    }



    @GetMapping("/add_channel")
    public String addChannel(Model model, @ModelAttribute("channel") ChannelCreateEditDto channel) {
        model.addAttribute("channel", channel);
        return "channel/add_channel";
    }


    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute @Validated ChannelCreateEditDto channel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("channel", channel);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/channels/add_channel";
        }


        return "redirect:/channels/" + channelService.create(channel).getId();
    }

    //    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated ChannelCreateEditDto channel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("channel", channel);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/channels/{id}";
        }

        return channelService.update(id, channel)
                .map(it -> "redirect:/channels/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!channelService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/channels";
    }



}
