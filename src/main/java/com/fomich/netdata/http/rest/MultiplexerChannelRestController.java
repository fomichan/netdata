package com.fomich.netdata.http.rest;

import com.fomich.netdata.dto.MultiplexerChannelCreateEditDto;
import com.fomich.netdata.service.MultiplexerChannelService;
import com.fomich.netdata.validation.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/v1/multiplexerchannels")
@RequiredArgsConstructor
public class MultiplexerChannelRestController {

    private final MultiplexerChannelService multiplexerChannelService;



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) // принимаем JSON и spring его преобразует в DTO
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Validated @RequestBody MultiplexerChannelCreateEditDto multiplexerChannel,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return ValidationUtils.prepareBadRequestResponse(bindingResult);
        }
        return ResponseEntity.ok(multiplexerChannelService.create(multiplexerChannel));
    }



    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        if (!multiplexerChannelService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }







}
