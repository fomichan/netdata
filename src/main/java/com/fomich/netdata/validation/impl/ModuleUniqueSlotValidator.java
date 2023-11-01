package com.fomich.netdata.validation.impl;

import com.fomich.netdata.database.repository.ModuleRepository;
import com.fomich.netdata.dto.ModuleCreateEditDto;
import com.fomich.netdata.validation.ModuleUniqueSlot;
import jakarta.persistence.Tuple;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ModuleUniqueSlotValidator implements ConstraintValidator<ModuleUniqueSlot, ModuleCreateEditDto> {

    //private final MultiplexerRepository multiplexerRepository;
    private final ModuleRepository moduleRepository;

    // Проверяем не занят ли уже этот слот мультиплексора
    @Override
    public boolean isValid(ModuleCreateEditDto value, ConstraintValidatorContext context) {



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


        if (slots.contains(value.getSlot())) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Slot " + value.getSlot() + " is already occupied by another module").addConstraintViolation();
            return false;
        }
        return true;


        //return !slots.contains(value.getSlot());

    }
}
