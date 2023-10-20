package com.fomich.netdata.http.controller;

import com.fomich.netdata.dto.*;
import com.fomich.netdata.service.MultiplexerChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String create(@ModelAttribute @Validated MultiplexerChannelCreateEditDto multiplexerChannel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
        } else {
            multiplexerChannelService.create(multiplexerChannel);
        }



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
