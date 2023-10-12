package com.fomich.netdata.dto;

import com.fomich.netdata.database.entity.Site;
import lombok.Value;

@Value
public class MultiplexerReadDto {
    Integer id;
    String name;
    String serialNumber;
    SiteReadDto site;

}
