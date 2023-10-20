package com.fomich.netdata.dto;

import com.fomich.netdata.database.entity.ModuleType;
import lombok.Value;

@Value
public class ModuleReadDto {
    Integer id;
    ModuleType moduleType;
    String serialNumber;
    Integer slot;
    Integer multiplexerId;

}
