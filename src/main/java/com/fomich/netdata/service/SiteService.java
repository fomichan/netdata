package com.fomich.netdata.service;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.repository.SiteRepository;
import com.fomich.netdata.dto.MultiplexerReadDto;
import com.fomich.netdata.dto.SiteReadDto;
import com.fomich.netdata.mapper.SiteReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteService {

    private final SiteRepository siteRepository;
    private final SiteReadMapper siteReadMapper;


    public List<SiteReadDto> findAll() {
        return siteRepository.findAll().stream()
                .map(siteReadMapper::map)
                .toList();
    }
}
