package com.fomich.netdata.http.rest;

import com.fomich.netdata.dto.*;
import com.fomich.netdata.service.MultiplexerService;
import com.fomich.netdata.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/multiplexers")
@RequiredArgsConstructor
public class MultiplexerRestController {

    private final MultiplexerService multiplexerService;
    private final SiteService siteService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE) // JSON по умолчанию

    public PageResponse<MultiplexerReadDto> findAll(
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

        return PageResponse.of(page);
    }


    @GetMapping("/{id}")
    public MultiplexerShowDetailsDto findById(@PathVariable("id") Integer id) {

        return multiplexerService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) // принимаем JSON и spring его преобразует в DTO
    @ResponseStatus(HttpStatus.CREATED)
    public MultiplexerReadDto create(@Validated @RequestBody MultiplexerCreateEditDto multiplexer) { // @RequestBody - данные в теле запроса

        return multiplexerService.create(multiplexer);
    }


    @PutMapping("/{id}")
    public MultiplexerReadDto update(@PathVariable("id") @Validated Integer id,
                         @Validated @RequestBody MultiplexerCreateEditDto multiplexer) {

        return multiplexerService.update(id, multiplexer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }



    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        if (!multiplexerService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }



}
