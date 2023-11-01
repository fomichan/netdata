package com.fomich.netdata.validation.impl;

import com.fomich.netdata.database.entity.MultiplexerChannel;
import com.fomich.netdata.database.repository.MultiplexerChannelRepository;
import com.fomich.netdata.dto.MultiplexerChannelCreateEditDto;
import com.fomich.netdata.validation.MuxChannel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MuxChannelValidator implements ConstraintValidator<MuxChannel, MultiplexerChannelCreateEditDto> {

    private final MultiplexerChannelRepository multiplexerChannelRepository;

    @Override
    public boolean isValid(MultiplexerChannelCreateEditDto value, ConstraintValidatorContext context) {

        Optional<MultiplexerChannel> optional = multiplexerChannelRepository.findMultiplexerChannelByMultiplexerIdAndAndChannelId(value.getMultiplexerId(), value.getChannelId());

        return optional.isEmpty();


    }
}
