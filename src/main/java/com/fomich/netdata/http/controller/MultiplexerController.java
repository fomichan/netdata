package com.fomich.netdata.http.controller;

import com.fomich.netdata.dto.*;
import com.fomich.netdata.service.MultiplexerService;
import com.fomich.netdata.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/multiplexers")
@RequiredArgsConstructor
public class MultiplexerController {

    private final MultiplexerService multiplexerService;
    private final SiteService siteService;

    @GetMapping
    public String findAll(Model model,
                          @RequestParam(name = "direction", defaultValue = "asc") String direction,
                          @RequestParam(name = "sort", defaultValue = "name") String sort,
                          @RequestParam(value = "page", defaultValue = "1") int pageNumber, // будем брать отсюда, а не из pageable чтобы начинался с 1
                          MultiplexerFilter filter,
                          Pageable pageable) {

        // Создадим объект Sort на основе параметров сортировки
        Sort sortObj = Sort.by(Sort.Direction.fromString(direction), sort);
        // Создадим объект Pageable с учетом сортировки
        Pageable pageableWithSort = PageRequest.of(pageNumber - 1, pageable.getPageSize(), sortObj);


        Page<MultiplexerReadDto> page = multiplexerService.findAll(filter, pageableWithSort);
        model.addAttribute("multiplexers", PageResponse.of(page));
        model.addAttribute("sites", siteService.findAllShort());
        model.addAttribute("filter", filter);
        return "channel/multiplexers";
    }



    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {


        Optional<MultiplexerShowDetailsDto> mux = multiplexerService.findById(id);


        mux.map(multiplexer -> {
            model.addAttribute("multiplexer", multiplexer);
            model.addAttribute("sites", siteService.findAllShort());
            return multiplexer;
        })
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return "channel/multiplexer";




        /*
        return multiplexerService.findById(id)
                .map(multiplexer -> {
                    model.addAttribute("multiplexer", multiplexer);
                    model.addAttribute("sites", siteService.findAll());
                    return "channel/multiplexer";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

         */

        /*
        return multiplexerService.findById(id)
                .map(multiplexer -> {
                    model.addAttribute("multiplexer", multiplexer);
                    model.addAttribute("sites", siteService.findAllShort());
                    return "channel/multiplexer";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

         */
    }


    @GetMapping("/add_multiplexer")
    public String addMultiplexer(Model model, @ModelAttribute("multiplexer") MultiplexerCreateEditDto multiplexer) {
        model.addAttribute("multiplexer", multiplexer);
        model.addAttribute("sites", siteService.findAllShort());
        return "channel/add_multiplexer";
    }


    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute @Validated MultiplexerCreateEditDto multiplexer,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("multiplexer", multiplexer);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/multiplexers/add_multiplexer";
        }

        multiplexerService.create(multiplexer);
        return "redirect:/multiplexers";

        //return "redirect:/multiplexers/" + multiplexerService.create(multiplexer).getId();
    }

//    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated MultiplexerCreateEditDto multiplexer,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("multiplexer", multiplexer);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/multiplexers/{id}";
        }


        return multiplexerService.update(id, multiplexer)
                .map(it -> "redirect:/multiplexers/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!multiplexerService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/multiplexers";
    }



}
