package com.fomich.netdata.mapper;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.dto.ChannelReadDto;
import com.fomich.netdata.dto.MultiplexerReadDto;
import com.fomich.netdata.dto.SiteReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChannelReadMapper implements Mapper<Channel, ChannelReadDto> {

    private final SiteReadMapper siteReadMapper;
    @Override
    public ChannelReadDto map(Channel object) {

        return new ChannelReadDto(
                object.getId(),
                object.getName()
        );
    }

}



