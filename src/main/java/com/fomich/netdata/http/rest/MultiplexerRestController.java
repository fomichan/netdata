package com.fomich.netdata.http.rest;

import com.fomich.netdata.dto.*;
import com.fomich.netdata.service.MultiplexerService;
import com.fomich.netdata.service.SiteService;
import com.fomich.netdata.validation.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/multiplexers")
@RequiredArgsConstructor
public class MultiplexerRestController {

    private final MultiplexerService multiplexerService;
    private final SiteService siteService;

    /*
    Пример JSON Pageable
    {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": [
            {
                "property": "property1",
                "direction": "ASC"
            },
            {
                "property": "property2",
                "direction": "DESC"
            }
        ]
    }

     */

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE) // JSON по умолчанию
    public PageResponse<MultiplexerReadDto> findAll(
                          MultiplexerFilter filter,
                          Pageable pageable) {

        Page<MultiplexerReadDto> page = multiplexerService.findAll(filter, pageable);
        return PageResponse.of(page);
    }


    @GetMapping("/{id}")
    public MultiplexerShowDetailsDto findById(@PathVariable("id") Integer id) {

        return multiplexerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) // принимаем JSON и spring его преобразует в DTO
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Validated @RequestBody MultiplexerCreateEditDto multiplexer,
                                     BindingResult bindingResult
                                     ) { // @RequestBody - данные в теле запроса

        if (bindingResult.hasErrors()) {

            return ValidationUtils.prepareBadRequestResponse(bindingResult);
        }
        return ResponseEntity.ok(multiplexerService.create(multiplexer));

        //return multiplexerService.create(multiplexer);
    }




    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") @Validated Integer id,
                         @Validated @RequestBody MultiplexerCreateEditDto multiplexer,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ValidationUtils.prepareBadRequestResponse(bindingResult);
        }
        return ResponseEntity.ok(multiplexerService.update(id, multiplexer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

//        return multiplexerService.update(id, multiplexer)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }



    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        if (!multiplexerService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }



}
