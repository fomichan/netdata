package com.fomich.netdata.dto;

import lombok.Value;

import java.util.List;

@Value
public class MultiplexerShowDetailsDto {
    Integer id;
    String name;
    String serialNumber;
    SiteReadDto site;
    List<ModuleReadDto> modules;
}
