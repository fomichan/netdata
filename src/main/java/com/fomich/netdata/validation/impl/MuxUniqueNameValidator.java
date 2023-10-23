package com.fomich.netdata.validation.impl;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.entity.MultiplexerChannel;
import com.fomich.netdata.database.repository.MultiplexerChannelRepository;
import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.dto.MultiplexerChannelCreateEditDto;
import com.fomich.netdata.dto.MultiplexerCreateEditDto;
import com.fomich.netdata.validation.MuxChannel;
import com.fomich.netdata.validation.MuxUniqueName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MuxUniqueNameValidator implements ConstraintValidator<MuxUniqueName, MultiplexerCreateEditDto> {

    private final MultiplexerRepository multiplexerRepository;

    @Override
    public boolean isValid(MultiplexerCreateEditDto value, ConstraintValidatorContext context) {

        //Optional<MultiplexerChannel> optional = multiplexerChannelRepository.findMultiplexerChannelByMultiplexerIdAndAndChannelId(value.getMultiplexerId(), value.getChannelId());
        Optional<Multiplexer> optional = multiplexerRepository.findByNameIgnoreCase(value.getName());


        if (optional.isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("A multiplexer named " + value.getName() + " already exists").addConstraintViolation();
            return false;
        }
        return true;




    }
}
