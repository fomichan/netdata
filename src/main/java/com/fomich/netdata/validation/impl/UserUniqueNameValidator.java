package com.fomich.netdata.validation.impl;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.entity.User;
import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.database.repository.UserRepository;
import com.fomich.netdata.dto.MultiplexerCreateEditDto;
import com.fomich.netdata.dto.UserCreateEditDto;
import com.fomich.netdata.validation.MuxUniqueName;
import com.fomich.netdata.validation.UserUniqueName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserUniqueNameValidator implements ConstraintValidator<UserUniqueName, UserCreateEditDto> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(UserCreateEditDto value, ConstraintValidatorContext context) {

        //Optional<MultiplexerChannel> optional = multiplexerChannelRepository.findMultiplexerChannelByMultiplexerIdAndAndChannelId(value.getMultiplexerId(), value.getChannelId());
        Optional<User> optional = userRepository.findByUsernameIgnoreCase(value.getUsername());


        if (optional.isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("A username " + value.getUsername() + " already exists").addConstraintViolation();
            return false;
        }
        return true;




    }
}
