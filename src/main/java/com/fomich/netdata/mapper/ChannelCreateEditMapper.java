package com.fomich.netdata.mapper;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.dto.ChannelCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelCreateEditMapper implements Mapper<ChannelCreateEditDto, Channel>{




    @Override
    public Channel map(ChannelCreateEditDto object) {
        Channel channel = new Channel();
        copy(object, channel);

        return channel;
    }


    // For update
    @Override
    public Channel map(ChannelCreateEditDto fromObject, Channel toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(ChannelCreateEditDto fromObject, Channel toObject) {
        toObject.setName(fromObject.getName());

    }

}
