package com.fomich.netdata.dto;

import com.fomich.netdata.database.entity.ModuleType;
import com.fomich.netdata.validation.ModuleUniqueSlot;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@ModuleUniqueSlot // валидация
@FieldNameConstants
public class ModuleCreateEditDto {

    @NotNull(message = "Specify the module type")
    @Enumerated(EnumType.STRING)
    ModuleType moduleType;


    String serialNumber;

    @NotNull(message = "Specify the slot number")
    @Min(value = 1, message = "The slot number must be between 1 and 25")
    @Max(value = 25, message = "The slot number must be between 1 and 25")
    Integer slot;

    @NotNull(message = "Multiplexer not specified")
    Integer multiplexerId;
}
