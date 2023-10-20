package com.fomich.netdata.mapper;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.entity.MultiplexerChannel;
import com.fomich.netdata.database.entity.Site;
import com.fomich.netdata.database.repository.ChannelRepository;
import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.database.repository.SiteRepository;
import com.fomich.netdata.dto.MultiplexerChannelCreateEditDto;
import com.fomich.netdata.dto.MultiplexerCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MultiplexerChannelCreateEditMapper implements Mapper<MultiplexerChannelCreateEditDto, MultiplexerChannel>{


    private final MultiplexerRepository multiplexerRepository;
    private final ChannelRepository channelRepository;


    @Override
    public MultiplexerChannel map(MultiplexerChannelCreateEditDto object) {
        MultiplexerChannel multiplexerChannel = new MultiplexerChannel();
        copy(object, multiplexerChannel);

        return multiplexerChannel;
    }


    // For update
     @Override
    public MultiplexerChannel map(MultiplexerChannelCreateEditDto fromObject, MultiplexerChannel toObject) {
        copy(fromObject, toObject);
        return toObject;
    }


    private void copy(MultiplexerChannelCreateEditDto fromObject, MultiplexerChannel toObject) {

        toObject.setMultiplexer(getMultiplexer(fromObject.getMultiplexerId()));
        toObject.setChannel(getChannel(fromObject.getChannelId()));
    }



    private Multiplexer getMultiplexer(Integer muxId) {
        return Optional.ofNullable(muxId)
                .flatMap(multiplexerRepository::findById)
                .orElse(null);

    }


    private Channel getChannel(Integer channelId) {
        return Optional.ofNullable(channelId)
                .flatMap(channelRepository::findById)
                .orElse(null);

    }



}
