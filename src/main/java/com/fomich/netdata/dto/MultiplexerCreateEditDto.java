package com.fomich.netdata.dto;

import com.fomich.netdata.validation.MuxUniqueName;
import com.fomich.netdata.validation.group.CreateAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants // чтобы сделать константы с названиями полей для тестов контроллера
@MuxUniqueName(groups = CreateAction.class) // валидация на уникальное имя
public class MultiplexerCreateEditDto {

    @NotBlank(message = "Specify the name of multiplexer")
    @Size(min = 3, max = 255, message = "Длина имени должна быть от 3 до 255 символов")
    String name;

    @Size(min = 3, max = 32, message = "Длина серийного номера должна быть от 3 до 32 символов")
    String serialNumber;

    @NotNull(message = "Specify the site name")
    Integer siteId;

}
