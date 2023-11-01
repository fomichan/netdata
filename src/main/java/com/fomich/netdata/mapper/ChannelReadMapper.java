package com.fomich.netdata.mapper;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.dto.ChannelReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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



