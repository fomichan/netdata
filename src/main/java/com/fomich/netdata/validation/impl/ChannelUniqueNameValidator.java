package com.fomich.netdata.validation.impl;

import com.fomich.netdata.database.entity.Channel;
import com.fomich.netdata.database.repository.ChannelRepository;
import com.fomich.netdata.dto.ChannelCreateEditDto;
import com.fomich.netdata.validation.ChannelUniqueName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChannelUniqueNameValidator implements ConstraintValidator<ChannelUniqueName, ChannelCreateEditDto> {

    private final ChannelRepository channelRepository;

    @Override
    public boolean isValid(ChannelCreateEditDto value, ConstraintValidatorContext context) {

        //Optional<MultiplexerChannel> optional = multiplexerChannelRepository.findMultiplexerChannelByMultiplexerIdAndAndChannelId(value.getMultiplexerId(), value.getChannelId());
        Optional<Channel> optional = channelRepository.findByNameIgnoreCase(value.getName());


        if (optional.isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("A channel named " + value.getName() + " already exists").addConstraintViolation();
            return false;
        }
        return true;




    }
}
