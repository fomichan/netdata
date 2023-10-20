package com.fomich.netdata.mapper;

import com.fomich.netdata.database.entity.Module;
import com.fomich.netdata.dto.ModuleReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModuleReadMapper implements Mapper<Module, ModuleReadDto> {

    @Override
    public ModuleReadDto map(Module object) {

        return new ModuleReadDto(
                object.getId(),
                object.getModuleType(),
                object.getSerialNumber(),
                object.getSlot(),
                object.getMultiplexer().getId()
        );
    }

}



