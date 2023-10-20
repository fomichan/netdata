package com.fomich.netdata.database.repository;

import com.fomich.netdata.database.entity.MultiplexerChannel;
import com.fomich.netdata.database.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MultiplexerChannelRepository extends JpaRepository<MultiplexerChannel, Integer> {

    //Optional<Site> findById(Integer id);

    Optional<MultiplexerChannel> findMultiplexerChannelByMultiplexerIdAndAndChannelId(Integer multiplexerId, Integer channelId);


}
