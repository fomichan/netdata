package com.fomich.netdata.mapper;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.dto.ModuleReadDto;
import com.fomich.netdata.dto.MultiplexerShowDetailsDto;
import com.fomich.netdata.dto.SiteReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MultiplexerShowDetailsMapper implements Mapper<Multiplexer, MultiplexerShowDetailsDto> {

    private final SiteReadMapper siteReadMapper;
    private final ModuleReadMapper moduleReadMapper;
    @Override
    public MultiplexerShowDetailsDto map(Multiplexer object) {

        SiteReadDto site = Optional.ofNullable(object.getSite())
                .map(siteReadMapper::map)
                .orElse(null);


        List<ModuleReadDto> modules =  object.getModules().stream()
                .map(moduleReadMapper::map)
                .sorted((mod1, mod2) -> mod1.getSlot().compareTo(mod2.getSlot()))
                .collect(Collectors.toList());

        return new MultiplexerShowDetailsDto(
                object.getId(),
                object.getName(),
                object.getSerialNumber(),
                site,
                modules
        );


    }
}
