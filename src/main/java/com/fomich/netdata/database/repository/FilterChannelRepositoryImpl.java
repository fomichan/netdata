package com.fomich.netdata.database.repository;


import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.entity.QChannel;
import com.fomich.netdata.database.entity.QMultiplexer;
import com.fomich.netdata.database.querydsl.QPredicates;
import com.fomich.netdata.dto.ChannelFilter;
import com.fomich.netdata.dto.MultiplexerFilter;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FilterChannelRepositoryImpl implements FilterChannelRepository {

    private final EntityManager entityManager;



    @Override
    public List<Channel> findAllByFilter(ChannelFilter filter) {
        var predicate = QPredicates.builder()
                .add(filter.name(), QChannel.channel.name::containsIgnoreCase)
                .add(filter.multiplexerId(), QChannel.channel.multiplexerChannels.any().multiplexer.id::eq)
                .build();

        return new JPAQuery<Channel>(entityManager)
                .select(QChannel.channel)
                .from(QChannel.channel)
                .where(predicate)
                .fetch();
    }
}
