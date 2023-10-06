package com.fomich.netdata.http.controller;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.dto.ChannelFilter;
import com.fomich.netdata.dto.ChannelReadDto;
import com.fomich.netdata.dto.MultiplexerFilter;
import com.fomich.netdata.dto.PageResponse;
import com.fomich.netdata.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @GetMapping
    public String findAll(Model model, ChannelFilter filter, Pageable pageable) { // спринг может предоставить pageable, у него есть для этого специальный argument resolver
                                                                                    // Для этого нужно передавать параметры с названиями page и size

        Page<ChannelReadDto> page = channelService.findAll(filter, pageable);
        model.addAttribute("channels", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "channel/channels";
    }
}
