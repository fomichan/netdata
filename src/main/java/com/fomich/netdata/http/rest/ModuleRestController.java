package com.fomich.netdata.http.rest;

import com.fomich.netdata.database.entity.ModuleType;
import com.fomich.netdata.dto.ModuleCreateEditDto;
import com.fomich.netdata.dto.ModuleReadDto;
import com.fomich.netdata.service.ModuleService;
import com.fomich.netdata.validation.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/v1/modules")
@RequiredArgsConstructor
public class ModuleRestController {


    private final ModuleService moduleService;


    @GetMapping("/{id}")
    public ModuleReadDto findById(@PathVariable("id") Integer id) {

        return moduleService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) // принимаем JSON и spring его преобразует в DTO
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Validated @RequestBody  ModuleCreateEditDto moduleCreateDto,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return ValidationUtils.prepareBadRequestResponse(bindingResult);
        }
        return ResponseEntity.ok(moduleService.create(moduleCreateDto));
    }





    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                         @Validated @RequestBody ModuleCreateEditDto muxModule,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ValidationUtils.prepareBadRequestResponse(bindingResult);
        }
        return ResponseEntity.ok(moduleService.update(id, muxModule)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }




    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        if (!moduleService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}
