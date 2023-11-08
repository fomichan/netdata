package com.fomich.netdata.dto;

import com.fomich.netdata.validation.SiteUniqueName;
import com.fomich.netdata.validation.group.CreateAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants // чтобы сделать константы с названиями полей для тестов контроллера
@SiteUniqueName(groups = CreateAction.class) // валидация на уникальное имя
public class SiteCreateEditDto {

    @NotBlank(message = "Specify the name of site")
    @Size(min = 3, max = 255, message = "Длина названия объекта должна быть от 3 до 255 символов")
    String name;

    @Size(max = 255, message = "Длина названия региона должна быть до 255 символов")
    String region;

    @Size(max = 255, message = "Длина названия населенного пункта должна быть до 255 символов")
    String city;

    @Size(max = 255, message = "Длина адреса должна быть до 255 символов")
    String address;
}