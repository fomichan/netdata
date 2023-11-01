package com.fomich.netdata.http.controller;

import com.fomich.netdata.database.entity.ModuleType;
import com.fomich.netdata.dto.ModuleCreateEditDto;
import com.fomich.netdata.dto.ModuleReadDto;
import com.fomich.netdata.service.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/modules")
@RequiredArgsConstructor
public class ModuleController {


    private final ModuleService moduleService;


    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {

        return moduleService.findById(id)
                .map(muxModule -> {
                    model.addAttribute("muxModule", muxModule);
                    model.addAttribute("moduleTypes", ModuleType.values());
                    return "channel/module";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/add_module")
    public String addModule(Model model, @ModelAttribute("muxModule") ModuleCreateEditDto muxModule) {
        model.addAttribute("muxModule", muxModule);
        model.addAttribute("moduleTypes", ModuleType.values());
        return "channel/add_module";
    }


    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute @Validated ModuleCreateEditDto moduleCreateDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("muxModule", moduleCreateDto);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/modules/add_module";
        }

        ModuleReadDto moduleReadDto = moduleService.create(moduleCreateDto);

        //return "redirect:/multiplexers/" + moduleReadDto.getMultiplexerId();
        return "redirect:/multiplexers/" + moduleCreateDto.getMultiplexerId();


    }




    /*
    //    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute ModuleCreateEditDto module) {
        return moduleService.update(id, module)
                .map(it -> "redirect:/multiplexers/" + module.getMultiplexerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

     */

    //    @PutMapping("/{id}")
    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute @Validated ModuleCreateEditDto muxModule,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("muxModule", muxModule);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/modules/{id}";
        }

        return moduleService.update(id, muxModule)
                .map(it -> "redirect:/multiplexers/" + muxModule.getMultiplexerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }





    //    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id,
                         @RequestParam("multiplexerId") Integer multiplexerId) {
        if (!moduleService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/multiplexers/" + multiplexerId; // сюда надо передать id мультиплексора
    }






    /*
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
        model.addAttribute("sites", siteService.findAll());
        model.addAttribute("filter", filter);
        return "channel/multiplexers";
    }

     */


    /*

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

     */





}
