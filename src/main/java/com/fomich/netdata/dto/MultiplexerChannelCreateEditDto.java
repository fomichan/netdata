package com.fomich.netdata.dto;

import com.fomich.netdata.validation.MuxChannel;
import lombok.Value;

@Value
@MuxChannel // валидация
public class MultiplexerChannelCreateEditDto {

    Integer multiplexerId;
    Integer channelId;

}
