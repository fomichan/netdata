package com.fomich.netdata.dto;

import com.fomich.netdata.validation.MuxUniqueName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants // чтобы сделать константы с названиями полей для тестов контроллера
@MuxUniqueName // валидация на уникальное имя
public class MultiplexerCreateEditDto {

    @NotBlank(message = "Specify the name of multiplexer")
    String name;
    String serialNumber;

    @NotNull(message = "Specify the site name")
    Integer siteId;

}
