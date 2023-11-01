package com.fomich.netdata.dto;

import lombok.Value;

@Value
public class MultiplexerReadDto {
    Integer id;
    String name;
    String serialNumber;
    SiteReadDto site;

}
