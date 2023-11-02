package com.fomich.netdata.http.rest;

import com.fomich.netdata.dto.PageResponse;
import com.fomich.netdata.dto.SiteCreateEditDto;
import com.fomich.netdata.dto.SiteFilter;
import com.fomich.netdata.dto.SiteReadDto;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/v1/sites")
@RequiredArgsConstructor
public class SiteRestController {


    private final SiteService siteService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE) // JSON по умолчанию
    public PageResponse<SiteReadDto> findAll(
                          SiteFilter filter,
                          Pageable pageable) {

        Page<SiteReadDto> page = siteService.findAll(filter, pageable);
        return PageResponse.of(page);
    }

    @GetMapping("/{id}")
    public SiteReadDto findById(@PathVariable("id") Integer id) {

        return siteService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) // принимаем JSON и spring его преобразует в DTO
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Validated @RequestBody SiteCreateEditDto site,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ValidationUtils.prepareBadRequestResponse(bindingResult);
        }
        return ResponseEntity.ok(siteService.create(site));

//        return siteService.create(site);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                         @Validated @RequestBody SiteCreateEditDto site,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ValidationUtils.prepareBadRequestResponse(bindingResult);
        }
        return ResponseEntity.ok(siteService.update(id, site)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

//        return siteService.update(id, site)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        if (!siteService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
