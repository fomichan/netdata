package com.fomich.netdata.dto;

import com.fomich.netdata.validation.ChannelUniqueName;
import com.fomich.netdata.validation.SiteUniqueName;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants // чтобы сделать константы с названиями полей для тестов контроллера
@ChannelUniqueName
public class ChannelCreateEditDto {

    @NotBlank(message = "Specify the name of channel")
    String name;

}