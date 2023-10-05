package com.fomich.netdata.http.controller;

import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.dto.MultiplexerCreateEditDto;
import com.fomich.netdata.dto.MultiplexerFilter;
import com.fomich.netdata.service.MultiplexerService;
import com.fomich.netdata.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/multiplexers")
@RequiredArgsConstructor
public class MultiplexerController {

    private final MultiplexerService multiplexerService;
    private final SiteService siteService;

    @GetMapping
    public String findAll(Model model, MultiplexerFilter filter) {
        model.addAttribute("multiplexers", multiplexerService.findAll(filter));
        model.addAttribute("filter", filter);
        return "channel/multiplexers";
    }



    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return multiplexerService.findById(id)
                .map(multiplexer -> {
                    model.addAttribute("multiplexer", multiplexer);
                    model.addAttribute("sites", siteService.findAll());
                    return "channel/multiplexer";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/add_multiplexer")
    public String registration(Model model, @ModelAttribute("user") MultiplexerCreateEditDto multiplexer) {
        model.addAttribute("multiplexer", multiplexer);
        model.addAttribute("sites", siteService.findAll());
        return "channel/add_multiplexer";
    }


    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute MultiplexerCreateEditDto multiplexer, RedirectAttributes redirectAttributes) {
//        if (true) {
//            redirectAttributes.addAttribute("username", user.getUsername());
//            redirectAttributes.addAttribute("firstname", user.getFirstname());
//            redirectAttributes.addFlashAttribute("user", user);
//            return "redirect:/users/registration";
//        }
        return "redirect:/multiplexers/" + multiplexerService.create(multiplexer).getId();
    }

//    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute MultiplexerCreateEditDto multiplexer) {
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
