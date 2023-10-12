package com.fomich.netdata.http.controller;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.dto.ChannelFilter;
import com.fomich.netdata.dto.ChannelReadDto;
import com.fomich.netdata.dto.MultiplexerFilter;
import com.fomich.netdata.dto.PageResponse;
import com.fomich.netdata.service.ChannelService;
import com.fomich.netdata.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;
    private final SiteService siteService;


    // спринг может предоставить pageable, у него есть для этого специальный argument resolver
    // Для этого нужно передавать параметры с названиями page и size
    @GetMapping
    public String findAll(Model model,
                          @RequestParam(name = "direction", defaultValue = "asc") String direction,
                          @RequestParam(name = "sort", defaultValue = "name") String sort,
                          @RequestParam(value = "page", defaultValue = "1") int pageNumber, // будем брать отсюда, а не из pageable чтобы начинался с 1
                          ChannelFilter filter,
                          Pageable pageable) {

        // Создайте объект Sort на основе параметров сортировки
        Sort sortObj = Sort.by(Sort.Direction.fromString(direction), sort);
        // Создайте объект Pageable с учетом сортировки
        Pageable pageableWithSort = PageRequest.of(pageNumber - 1, pageable.getPageSize(), sortObj);

        Page<ChannelReadDto> page = channelService.findAll(filter, pageableWithSort);
        model.addAttribute("channels", PageResponse.of(page));
        model.addAttribute("sites", siteService.findAll());
        model.addAttribute("filter", filter);
        return "channel/channels";
    }
}
