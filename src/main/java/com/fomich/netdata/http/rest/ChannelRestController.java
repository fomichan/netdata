package com.fomich.netdata.http.rest;

import com.fomich.netdata.dto.*;
import com.fomich.netdata.service.ChannelService;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/v1/channels")
@RequiredArgsConstructor
public class ChannelRestController {

    private final ChannelService channelService;
    private final SiteService siteService;
    private final MultiplexerService multiplexerService;




    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PageResponse<ChannelReadDto> findAll(
                          ChannelFilter filter,
                          Pageable pageable) {

        Page<ChannelReadDto> page = channelService.findAll(filter, pageable);
        return PageResponse.of(page);
    }



    @GetMapping("/{id}")
    public ChannelShowDetailsDto findById(@PathVariable("id") Integer id) {

        return channelService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }




    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Validated @RequestBody ChannelCreateEditDto channel,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return ValidationUtils.prepareBadRequestResponse(bindingResult);
        }
        return ResponseEntity.ok(channelService.create(channel));

//        return channelService.create(channel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                         @Validated @RequestBody ChannelCreateEditDto channel,
                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return ValidationUtils.prepareBadRequestResponse(bindingResult);
        }
        return ResponseEntity.ok(channelService.update(id, channel)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

//        return channelService.update(id, channel)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {
        if (!channelService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
