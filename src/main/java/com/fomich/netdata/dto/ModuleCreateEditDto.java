package com.fomich.netdata.dto;

import com.fomich.netdata.database.entity.ModuleType;
import com.fomich.netdata.validation.ModuleUniqueSlot;
import lombok.Value;

@Value
@ModuleUniqueSlot // валидация
public class ModuleCreateEditDto {

    ModuleType moduleType;
    String serialNumber;
    Integer slot;
    Integer multiplexerId;
}
