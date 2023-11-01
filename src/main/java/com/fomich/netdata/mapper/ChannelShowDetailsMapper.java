package com.fomich.netdata.mapper;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.entity.MultiplexerChannel;
import com.fomich.netdata.dto.ChannelShowDetailsDto;
import com.fomich.netdata.dto.MultiplexerChannelReadDto;
import com.fomich.netdata.dto.MultiplexerReadDto;
import com.fomich.netdata.dto.MultiplexerReadDtoWithMCId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class ChannelShowDetailsMapper implements Mapper<Channel, ChannelShowDetailsDto> {


    private final MultiplexerReadMapper multiplexerReadMapper;
    private final MultiplexerChannelReadMapper mcReadMapper;



    @Override
    public ChannelShowDetailsDto map(Channel object) {


        List<MultiplexerReadDto> multiplexers = object.getMultiplexerChannels().stream()
                .map(MultiplexerChannel::getMultiplexer)
                .map(multiplexerReadMapper::map)
                .collect(Collectors.toList());

        List<MultiplexerChannelReadDto> multiplexerChennels = object.getMultiplexerChannels().stream()
                .map(mcReadMapper::map)
                .toList();


        // Объединим MultiplexerReadDto и MultiplexerChennelReadDto в MultiplexerReadDtoWithMCId (в MultiplexerReadDto добавить поле multiplexerChannelId)

        List<MultiplexerReadDtoWithMCId> multiplexersWithMC = Stream.concat(
                        multiplexers.stream().map(mux -> new MultiplexerReadDtoWithMCId(mux.getId(),
                                mux.getName(),
                                mux.getSerialNumber(),
                                mux.getSite(),
                                null)), // Преобразуем MultiplexerReadDto в MultiplexerReadDtoWithMCId

                        multiplexerChennels.stream().map(muxCh -> new MultiplexerReadDtoWithMCId(muxCh.getMultiplexerId(),
                                null,
                                null,
                                null,
                                muxCh.getId())) // Преобразуем MultiplexerChennelReadDto в MultiplexerReadDtoWithMCId
                )
                .collect(Collectors.toMap(
                        obj -> obj.getId(), // Ключ - это id
                        obj -> obj, // Значение - объект TypeC
                        (c1, c2) -> {       // mergeFunction Это бинарная функция, которая решает, как разрешать конфликты, если ключи в потоке дублируются.
                            if (c1.getMcId() == null) {
                                c1.setMcId(c2.getMcId()); // если это объект созданный из multiplexers (без mcId), то добавим mcId из c2, и вернем с1
                            }
                            return c1;
                        }
                ))
                .values() // Получаем коллекцию значений из мапы
                .stream()
                .collect(Collectors.toList());




        return new ChannelShowDetailsDto(
                object.getId(),
                object.getName(),
                multiplexersWithMC
        );





    }
}
