package com.fomich.netdata.database.repository;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.dto.ChannelFilter;
import com.fomich.netdata.dto.MultiplexerFilter;

import java.util.List;

public interface FilterChannelRepository {

    List<Channel> findAllByFilter(ChannelFilter filter);
}
