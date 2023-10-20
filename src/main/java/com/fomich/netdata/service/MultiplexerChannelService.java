package com.fomich.netdata.service;


import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.entity.MultiplexerChannel;
import com.fomich.netdata.database.repository.ChannelRepository;
import com.fomich.netdata.database.repository.MultiplexerChannelRepository;
import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.dto.MultiplexerChannelCreateEditDto;
import com.fomich.netdata.dto.MultiplexerChannelReadDto;
import com.fomich.netdata.dto.MultiplexerCreateEditDto;
import com.fomich.netdata.dto.MultiplexerReadDto;
import com.fomich.netdata.mapper.MultiplexerChannelCreateEditMapper;
import com.fomich.netdata.mapper.MultiplexerChannelReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MultiplexerChannelService {

    private final ChannelRepository channelRepository;
    private final MultiplexerRepository multiplexerRepository;
    private final MultiplexerChannelRepository multiplexerChannelRepository;
    private final MultiplexerChannelCreateEditMapper multiplexerChannelCreateEditMapper;
    private final MultiplexerChannelReadMapper multiplexerChannelReadMapper;



    /*
    @Transactional
    public void create(Integer channelId, Integer muxId) {


        Channel channel = channelRepository.findById(channelId).orElse(null);
        Multiplexer multiplexer = multiplexerRepository.findById(muxId).orElse(null);

        multiplexerChannelRepository.save(new MultiplexerChannel());


        if (channel != null && multiplexer != null) {
            MultiplexerChannel multiplexerChannel = new MultiplexerChannel();
            multiplexerChannel.setChannel(channel);
            multiplexerChannel.setMultiplexer(multiplexer);
        }
    }

     */


    @Transactional
    public MultiplexerChannelReadDto create(MultiplexerChannelCreateEditDto muxChanDto) {

        // TODO: 20.10.2023 Проверить что в БД уже нет сущности с таким сочетанием ID



        return Optional.of(muxChanDto)
                .map(multiplexerChannelCreateEditMapper::map)
                .map(multiplexerChannelRepository::saveAndFlush)
                .map(multiplexerChannelReadMapper::map)
                .orElseThrow();
    }


    @Transactional
    public boolean delete(Integer id) {
        return multiplexerChannelRepository.findById(id)
                .map(entity -> {
                    multiplexerChannelRepository.delete(entity);
                    multiplexerChannelRepository.flush();
                    return true;
                })
                .orElse(false);
    }

}
