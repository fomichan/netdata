package com.fomich.netdata.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants // чтобы сделать константы с названиями полей для тестов контроллера
public class MultiplexerCreateEditDto {

    String name;
    Integer serialNumber;
    Integer siteId;

}
