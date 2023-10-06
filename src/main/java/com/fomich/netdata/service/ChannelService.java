package com.fomich.netdata.service;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.repository.ChannelRepository;
import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.dto.*;
import com.fomich.netdata.mapper.ChannelReadMapper;
import com.fomich.netdata.mapper.MultiplexerCreateEditMapper;
import com.fomich.netdata.mapper.MultiplexerReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChannelService {


    private final ChannelRepository channelRepository;
    private final ChannelReadMapper channelReadMapper;


    public Page<ChannelReadDto> findAll(ChannelFilter filter, Pageable pageable) {
        Page<Channel> channels = channelRepository.findAllByMultiplexerId(pageable, filter.multiplexerId());
        Page<ChannelReadDto> channelReadDtos = channels.map(channelReadMapper::map);

//        return channelRepository.findAllByMultiplexerId(pageable, filter.multiplexerId())
//                .map(channelReadMapper::map);
        return channelReadDtos;
    }





//    public List<MultiplexerReadDto> findAll() {
//        return channelRepository.findAll().stream()
//                .map(multiplexerReadMapper::map)
//                .toList();
//    }




//    public Optional<MultiplexerReadDto> findById(Integer id) {
//        return multiplexerRepository.findById(id)
//                .map(multiplexerReadMapper::map);
//    }
//
//
//    @Transactional
//    public MultiplexerReadDto create(MultiplexerCreateEditDto muxDto) {
//        return Optional.of(muxDto)
//                .map(multiplexerCreateEditMapper::map)
//                .map(multiplexerRepository::save)
//                .map(multiplexerReadMapper::map)
//                .orElseThrow();
//    }
//
//
//    @Transactional
//    public Optional<MultiplexerReadDto> update(Integer id, MultiplexerCreateEditDto muxDto) {
//        return multiplexerRepository.findById(id)
//                .map(entity -> multiplexerCreateEditMapper.map(muxDto, entity))
//                .map(multiplexerRepository::saveAndFlush)
//                .map(multiplexerReadMapper::map);
//    }
//
//
//    @Transactional
//    public boolean delete(Integer id) {
//        return multiplexerRepository.findById(id)
//                .map(entity -> {
//                    multiplexerRepository.delete(entity);
//                    multiplexerRepository.flush();
//                    return true;
//                })
//                .orElse(false);
//    }

}
