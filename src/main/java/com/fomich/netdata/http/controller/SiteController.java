package com.fomich.netdata.http.controller;

import com.fomich.netdata.dto.PageResponse;
import com.fomich.netdata.dto.SiteCreateEditDto;
import com.fomich.netdata.dto.SiteFilter;
import com.fomich.netdata.dto.SiteReadDto;
import com.fomich.netdata.service.SiteService;
import com.fomich.netdata.validation.group.CreateAction;
import com.fomich.netdata.validation.group.UpdateAction;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sites")
@RequiredArgsConstructor
public class SiteController {


    private final SiteService siteService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or hasAuthority('MANAGER')")
    public String findAll(Model model,
                          @RequestParam(name = "direction", defaultValue = "asc") String direction,
                          @RequestParam(name = "sort", defaultValue = "name") String sort,
                          @RequestParam(value = "page", defaultValue = "1") int pageNumber, // будем брать отсюда, а не из pageable чтобы начинался с 1
                          SiteFilter filter,
                          Pageable pageable) {

        // Создадим объект Sort на основе параметров сортировки
        Sort sortObj = Sort.by(Sort.Direction.fromString(direction), sort);
        // Создадим объект Pageable с учетом сортировки
        Pageable pageableWithSort = PageRequest.of(pageNumber - 1, pageable.getPageSize(), sortObj);


        Page<SiteReadDto> page = siteService.findAll(filter, pageableWithSort);

        model.addAttribute("sites", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "channel/sites";
    }



    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') or hasAuthority('MANAGER')")
    public String findById(@PathVariable("id") Integer id, Model model) {

        return siteService.findById(id)
                .map(site -> {
                    model.addAttribute("site", site);

                    return "channel/site";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/add_site")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addSite(Model model, @ModelAttribute("site") SiteCreateEditDto site) {
        model.addAttribute("site", site);
        return "channel/add_site";
    }


    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String create(@ModelAttribute @Validated({Default.class, CreateAction.class}) SiteCreateEditDto site,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("site", site);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/sites/add_site";
        }

        siteService.create(site);
        return "redirect:/sites";
    }

//    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated({Default.class, UpdateAction.class}) SiteCreateEditDto site,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("site", site);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/sites/{id}";
        }

        return siteService.update(id, site)
                .map(it -> "redirect:/sites/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@PathVariable("id") Integer id) {
        if (!siteService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/sites";
    }



}
