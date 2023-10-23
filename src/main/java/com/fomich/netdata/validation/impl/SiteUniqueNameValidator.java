package com.fomich.netdata.validation.impl;

import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.entity.Site;
import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.database.repository.SiteRepository;
import com.fomich.netdata.dto.MultiplexerCreateEditDto;
import com.fomich.netdata.dto.SiteCreateEditDto;
import com.fomich.netdata.validation.MuxUniqueName;
import com.fomich.netdata.validation.SiteUniqueName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SiteUniqueNameValidator implements ConstraintValidator<SiteUniqueName, SiteCreateEditDto> {

    private final SiteRepository siteRepository;

    @Override
    public boolean isValid(SiteCreateEditDto value, ConstraintValidatorContext context) {

        //Optional<MultiplexerChannel> optional = multiplexerChannelRepository.findMultiplexerChannelByMultiplexerIdAndAndChannelId(value.getMultiplexerId(), value.getChannelId());
        Optional<Site> optional = siteRepository.findByNameIgnoreCase(value.getName());


        if (optional.isPresent()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("A site named " + value.getName() + " already exists").addConstraintViolation();
            return false;
        }
        return true;




    }
}
