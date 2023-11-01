package com.fomich.netdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//@Value
@Data // изменил @Value на @Data ChannelShowDetailsMapper нужно чтобы сущность была изменяемая
@AllArgsConstructor
public class MultiplexerReadDtoWithMCId {
    Integer id;
    String name;
    String serialNumber;
    SiteReadDto site;
    Integer mcId;

}
