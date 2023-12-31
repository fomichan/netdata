package com.fomich.netdata.database.repository;

import com.fomich.netdata.database.entity.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface ChannelRepository extends
        JpaRepository<Channel, Integer>,
        FilterChannelRepository,
        QuerydslPredicateExecutor<Channel> {


//    @Query("select m from Multiplexer m join m.multiplexerChannels mc where m.name = :name2") // fetch не срабатывает
//    Optional<Channel> findByName(@Param("name2") String name);


    @Query("select c from Channel c join c.multiplexerChannels mc where mc.multiplexer.id = :id")
    Page<Channel> findAllByMultiplexerId(Pageable pageable, Integer id);



    // Для валидации
    Optional<Channel> findByNameIgnoreCase(String name);


}
