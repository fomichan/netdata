package com.fomich.netdata.dto;

import lombok.Value;

import java.util.List;

@Value
public class ChannelShowDetailsDto {
    Integer id;
    String name;
    List<MultiplexerReadDtoWithMCId> multiplexers;
    //List<MultiplexerReadDto> multiplexers;
    //ist<MultiplexerChennelReadDto> multiplexerChennels;
}
