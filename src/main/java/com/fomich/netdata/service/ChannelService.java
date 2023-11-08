package com.fomich.netdata.service;

import com.fomich.netdata.database.entity.QChannel;
import com.fomich.netdata.database.querydsl.QPredicates;
import com.fomich.netdata.database.repository.ChannelRepository;
import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.dto.ChannelCreateEditDto;
import com.fomich.netdata.dto.ChannelFilter;
import com.fomich.netdata.dto.ChannelReadDto;
import com.fomich.netdata.dto.ChannelShowDetailsDto;
import com.fomich.netdata.mapper.ChannelCreateEditMapper;
import com.fomich.netdata.mapper.ChannelReadMapper;
import com.fomich.netdata.mapper.ChannelShowDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChannelService {


    private final ChannelRepository channelRepository;
    private final MultiplexerRepository multiplexerRepository;
    private final ChannelReadMapper channelReadMapper;
    private final ChannelShowDetailsMapper channelShowDetailsMapper;
    private final ChannelCreateEditMapper channelCreateEditMapper;










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


    public Optional<ChannelShowDetailsDto> findById(Integer id) {
        return channelRepository.findById(id)
                .map(channelShowDetailsMapper::map);
    }



    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public ChannelReadDto create(ChannelCreateEditDto channel) {
        return Optional.of(channel)
                .map(channelCreateEditMapper::map)
                .map(channelRepository::save)
                .map(channelReadMapper::map)
                .orElseThrow();
    }


    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<ChannelReadDto> update(Integer id, ChannelCreateEditDto channel) {
        return channelRepository.findById(id)
                .map(entity -> channelCreateEditMapper.map(channel, entity))
                .map(channelRepository::saveAndFlush)
                .map(channelReadMapper::map);
    }


    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean delete(Integer id) {
        return channelRepository.findById(id)
                .map(entity -> {
                    channelRepository.delete(entity);
                    channelRepository.flush();
                    return true;
                })
                .orElse(false);
    }









}
