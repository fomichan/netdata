package com.fomich.netdata.dto;

import com.fomich.netdata.validation.MuxUniqueName;
import com.fomich.netdata.validation.SiteUniqueName;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants // чтобы сделать константы с названиями полей для тестов контроллера
@SiteUniqueName
public class SiteCreateEditDto {

    @NotBlank(message = "Specify the name of site")
    String name;
    String region;
    String city;
    String address;
}