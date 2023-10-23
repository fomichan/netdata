package com.fomich.netdata.mapper;

import com.fomich.netdata.database.entity.Site;
import com.fomich.netdata.dto.SiteReadDto;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;


@Component
public class SiteReadMapper implements Mapper<Site, SiteReadDto> {


    @Override
    public SiteReadDto map(Site object) {
        return new SiteReadDto(
                object.getId(),
                object.getName(),
                object.getRegion(),
                object.getCity(),
                object.getAddress()
        );
    }
}
