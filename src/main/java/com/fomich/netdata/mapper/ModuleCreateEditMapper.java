package com.fomich.netdata.mapper;

import com.fomich.netdata.database.entity.Module;
import com.fomich.netdata.database.entity.Multiplexer;
import com.fomich.netdata.database.repository.MultiplexerRepository;
import com.fomich.netdata.dto.ModuleCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ModuleCreateEditMapper implements Mapper<ModuleCreateEditDto, Module>{


    private final MultiplexerRepository multiplexerRepository;


    @Override
    public Module map(ModuleCreateEditDto object) {
        Module module = new Module();
        copy(object, module);

        return module;
    }


    // For update
    @Override
    public Module map(ModuleCreateEditDto fromObject, Module toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(ModuleCreateEditDto fromObject, Module toObject) {

        toObject.setModuleType(fromObject.getModuleType());
        toObject.setSerialNumber(fromObject.getSerialNumber());
        toObject.setSlot(fromObject.getSlot());
        //toObject.setSite(getSite(fromObject.getSiteId()));
        toObject.setMultiplexer(getMultiplexer(fromObject.getMultiplexerId()));
    }


    private Multiplexer getMultiplexer(Integer multiplexerId) {
        return Optional.ofNullable(multiplexerId)
                .flatMap(multiplexerRepository::findById)
                .orElse(null);

    }


}
