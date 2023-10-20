package com.fomich.netdata.http.controller;

import com.fomich.netdata.dto.*;
import com.fomich.netdata.service.MultiplexerChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/multiplexerchannels")
@RequiredArgsConstructor
public class MultiplexerChannelController {

    private final MultiplexerChannelService multiplexerChannelService;



    /*
    @PostMapping
    public String create(@ModelAttribute("channelId") Integer channelId, @ModelAttribute("multiplexerId") Integer multiplexerId) {

        multiplexerChannelService.create(channelId, multiplexerId);

        return "redirect:/channels/" + channelId;
    }

     */


    @PostMapping
    public String create(@ModelAttribute MultiplexerChannelCreateEditDto multiplexerChannel) {

        multiplexerChannelService.create(multiplexerChannel);

        return "redirect:/channels/" + multiplexerChannel.getChannelId();
    }







    //    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id, @ModelAttribute("channelId") Integer channelId) {
        if (!multiplexerChannelService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/channels/" + channelId;
    }







}
