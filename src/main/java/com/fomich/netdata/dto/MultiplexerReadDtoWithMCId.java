package com.fomich.netdata.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.Value;

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
