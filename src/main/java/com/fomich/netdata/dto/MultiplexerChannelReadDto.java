package com.fomich.netdata.dto;

import lombok.Value;

@Value
public class MultiplexerChannelReadDto {
    Integer id;
    Integer multiplexerId;
    Integer channelId;

}
