package com.fomich.netdata.mapper;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.dto.MultiplexerReadDto;
import com.fomich.netdata.dto.SiteReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MultiplexerReadMapper implements Mapper<Multiplexer, MultiplexerReadDto> {

    private final SiteReadMapper siteReadMapper;
    @Override
    public MultiplexerReadDto map(Multiplexer object) {
        SiteReadDto site = Optional.ofNullable(object.getSite())
                .map(siteReadMapper::map)
                .orElse(null);

        return new MultiplexerReadDto(
                object.getId(),
                object.getName(),
                object.getSerialNumber(),
                site
        );
    }
}
