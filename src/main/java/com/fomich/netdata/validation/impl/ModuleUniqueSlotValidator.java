package com.fomich.netdata.validation.impl;

import com.fomich.netdata.database.entity.Module;
import com.fomich.netdata.database.entity.MultiplexerChannel;
import com.fomich.netdata.database.repository.ModuleRepository;
import com.fomich.netdata.database.repository.MultiplexerChannelRepository;
import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.dto.ModuleCreateEditDto;
import com.fomich.netdata.dto.MultiplexerChannelCreateEditDto;
import com.fomich.netdata.validation.ModuleUniqueSlot;
import com.fomich.netdata.validation.MuxChannel;
import jakarta.persistence.Tuple;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModuleUniqueSlotValidator implements ConstraintValidator<ModuleUniqueSlot, ModuleCreateEditDto> {

    //private final MultiplexerRepository multiplexerRepository;
    private final ModuleRepository moduleRepository;

    // Проверяем не занят ли уже этот слот мультиплексора
    @Override
    public boolean isValid(ModuleCreateEditDto value, ConstraintValidatorContext context) {


//       List<Integer> slots = multiplexerRepository.findSlotById(value.getMultiplexerId());

//        List<Integer> slots = moduleRepository.findSlotByMultiplexerId(value.getMultiplexerId());




        List<Tuple> tuples = moduleRepository.findModuleSlotByMultiplexerId(value.getMultiplexerId());
        List<Integer> slots = tuples.stream()
                .map(tuple -> tuple.get(0, Integer.class))
                .toList();


        /*
        // Если вытащить modules целиком
        List<Module> modules = moduleRepository.findAllByMultiplexerId(value.getMultiplexerId());
        List<Integer> slots = modules.stream()
                .map(Module::getSlot)
                .toList();

         */


        return !slots.contains(value.getSlot());

    }
}
