package com.fomich.netdata.mapper;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.entity.Site;
import com.fomich.netdata.database.repository.SiteRepository;
import com.fomich.netdata.dto.MultiplexerCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChannelCreateEditMapper implements Mapper<MultiplexerCreateEditDto, Multiplexer>{

    private final SiteRepository siteRepository;


    @Override
    public Multiplexer map(MultiplexerCreateEditDto object) {
        Multiplexer multiplexer = new Multiplexer();
        copy(object, multiplexer);

        return multiplexer;
    }


    // For update
    @Override
    public Multiplexer map(MultiplexerCreateEditDto fromObject, Multiplexer toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(MultiplexerCreateEditDto fromObject, Multiplexer toObject) {
        toObject.setName(fromObject.getName());
        toObject.setSerialNumber(fromObject.getSerialNumber());
        toObject.setSite(getSite(fromObject.getSiteId()));

    }

    private Site getSite(Integer siteId) {
        return Optional.ofNullable(siteId)
                .flatMap(siteRepository::findById)
                .orElse(null);

    }


}
