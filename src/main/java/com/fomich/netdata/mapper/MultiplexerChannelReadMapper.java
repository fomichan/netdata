package com.fomich.netdata.mapper;

import com.fomich.netdata.database.entity.MultiplexerChannel;
import com.fomich.netdata.dto.MultiplexerChannelReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MultiplexerChannelReadMapper implements Mapper<MultiplexerChannel, MultiplexerChannelReadDto> {

    private final SiteReadMapper siteReadMapper;
    @Override
    public MultiplexerChannelReadDto map(MultiplexerChannel object) {

        return new MultiplexerChannelReadDto(
                object.getId(),
                object.getMultiplexer().getId(),
                object.getChannel().getId()
        );

    }
}
