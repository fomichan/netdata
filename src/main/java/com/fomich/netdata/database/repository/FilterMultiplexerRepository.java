package com.fomich.netdata.database.repository;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.dto.MultiplexerFilter;
import com.fomich.netdata.dto.MultiplexerReadDto;

import java.util.List;

public interface FilterMultiplexerRepository {

    List<Multiplexer> findAllByFilter(MultiplexerFilter filter);


//    List<MultiplexerReadDto> findAllByChannelId(Integer channelId);
}
