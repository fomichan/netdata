package com.fomich.netdata.mapper;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.entity.Site;
import com.fomich.netdata.database.repository.SiteRepository;
import com.fomich.netdata.dto.MultiplexerCreateEditDto;
import com.fomich.netdata.dto.SiteCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SiteCreateEditMapper implements Mapper<SiteCreateEditDto, Site>{




    @Override
    public Site map(SiteCreateEditDto object) {
        Site site = new Site();
        copy(object, site);

        return site;
    }


    // For update
    @Override
    public Site map(SiteCreateEditDto fromObject, Site toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(SiteCreateEditDto fromObject, Site toObject) {
        toObject.setName(fromObject.name());
        toObject.setRegion(fromObject.region());
        toObject.setCity(fromObject.city());
        toObject.setAddress(fromObject.address());


    }




}
