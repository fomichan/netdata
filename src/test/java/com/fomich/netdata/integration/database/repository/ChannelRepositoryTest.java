package com.fomich.netdata.integration.database.repository;

import com.fomich.netdata.database.repository.ChannelRepository;
import com.fomich.netdata.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ChannelRepositoryTest extends IntegrationTestBase {



    private final ChannelRepository channelRepository;


    @Test
    void checkFindAllByMultiplexerId() {

        var pageable = PageRequest.of(0, 5, Sort.by("id"));
        var page = channelRepository.findAllByMultiplexerId(pageable, 3);
        page.forEach(channel -> System.out.println(channel.getName()));

        while (page.hasNext()) {
            page = channelRepository.findAllByMultiplexerId(page.nextPageable(), 3);
            page.forEach(channel -> System.out.println(channel.getId() + " ::: " + channel.getName()));
        }
    }


}
