package com.fomich.netdata.service;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.entity.QChannel;
import com.fomich.netdata.database.entity.QMultiplexer;
import com.fomich.netdata.database.entity.QMultiplexerChannel;
import com.fomich.netdata.database.querydsl.QPredicates;
import com.fomich.netdata.database.repository.ChannelRepository;
import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.dto.*;
import com.fomich.netdata.mapper.ChannelReadMapper;
import com.fomich.netdata.mapper.MultiplexerCreateEditMapper;
import com.fomich.netdata.mapper.MultiplexerReadMapper;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    // запрос в HQL Repository
//    public Page<ChannelReadDto> findAll(ChannelFilter filter, Pageable pageable) {
//        Page<Channel> channels = channelRepository.findAllByMultiplexerId(pageable, filter.multiplexerId());
//        Page<ChannelReadDto> channelReadDtos = channels.map(channelReadMapper::map);
//        return channelReadDtos;
//    }



    public Page<ChannelReadDto> findAll(ChannelFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.name(), QChannel.channel.name::containsIgnoreCase)
                .add(filter.multiplexerId(), QChannel.channel.multiplexerChannels.any().multiplexer.id::eq)
                .add(filter.siteId(), QChannel.channel.multiplexerChannels.any().multiplexer.site.id::eq)
                .build();

//        Predicate predicate2 = QChannel.channel.id.in(
//                JPAExpressions
//                        .select(QMultiplexerChannel.multiplexerChannel.channel.id)
//                        .from(QMultiplexerChannel.multiplexerChannel)
//                        .innerJoin(QMultiplexerChannel.multiplexerChannel.multiplexer, QMultiplexer.multiplexer)
//                        .where(QMultiplexer.multiplexer.site.id.eq(filter.siteId())));

        //Добавим сортировку к pageable
//        pageable = PageRequest.of(
//                pageable.getPageNumber(),
//                pageable.getPageSize(),
//                Sort.by("name").ascending()
//        );

        Page<ChannelReadDto> page = channelRepository.findAll(predicate, pageable)
                .map(channelReadMapper::map);
        return page;

    }


    public List<ChannelReadDto> findAll(ChannelFilter filter) {
        List<ChannelReadDto> list = channelRepository.findAllByFilter(filter).stream()
                .map(channelReadMapper::map)
                .toList();
        return list;
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
