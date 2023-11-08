package com.fomich.netdata.dto;

import com.fomich.netdata.validation.ChannelUniqueName;
import com.fomich.netdata.validation.group.CreateAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants // чтобы сделать константы с названиями полей для тестов контроллера
@ChannelUniqueName(groups = CreateAction.class)
public class ChannelCreateEditDto {

    @NotBlank(message = "Specify the name of channel")
    @Size(min = 3, max = 255, message = "Длина должна быть от 3 до 255 символов")
    String name;

}