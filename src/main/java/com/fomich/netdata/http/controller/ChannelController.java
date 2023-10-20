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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
                    model.addAttribute("sites", siteService.findAll());

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
        model.addAttribute("sites", siteService.findAll());
        model.addAttribute("filter", filter);
        return "channel/channels";
    }
}
