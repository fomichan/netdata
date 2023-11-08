package com.fomich.netdata.http.controller;

import com.fomich.netdata.database.entity.Role;
import com.fomich.netdata.dto.PageResponse;
import com.fomich.netdata.dto.UserCreateEditDto;
import com.fomich.netdata.dto.UserFilter;
import com.fomich.netdata.dto.UserReadDto;
import com.fomich.netdata.service.security.UserService;
import com.fomich.netdata.validation.group.CreateAction;
import com.fomich.netdata.validation.group.UpdateAction;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public String findAll(Model model,
                          @RequestParam(name = "direction", defaultValue = "asc") String direction,
                          @RequestParam(name = "sort", defaultValue = "username") String sort,
                          @RequestParam(value = "page", defaultValue = "1") int pageNumber, // будем брать отсюда, а не из pageable чтобы начинался с 1
                          UserFilter filter, Pageable pageable) {

        // Создадим объект Sort на основе параметров сортировки
        Sort sortObj = Sort.by(Sort.Direction.fromString(direction), sort);
        // Создадим объект Pageable с учетом сортировки
        Pageable pageableWithSort = PageRequest.of(pageNumber - 1, pageable.getPageSize(), sortObj);


        Page<UserReadDto> page = userService.findAll(filter, pageableWithSort);
        model.addAttribute("users", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "user/users";
    }



    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGER') or hasAuthority('ADMIN')")
    public String findById(@PathVariable("id") Long id, Model model) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("roles", Role.values());
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/registration")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "user/registration";
    }

    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('MANAGER')")
    public String create(@ModelAttribute @Validated({Default.class, CreateAction.class}) UserCreateEditDto user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }
        userService.create(user);
        return "redirect:/users";
    }





    //    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute @Validated({Default.class, UpdateAction.class}) UserCreateEditDto user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/{id}";
        }


        return userService.update(id, user)
                .map(it -> "redirect:/users")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }





    //    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('MANAGER')")
    public String delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }


}
