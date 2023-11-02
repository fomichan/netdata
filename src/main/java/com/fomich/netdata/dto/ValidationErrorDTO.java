package com.fomich.netdata.dto;

import com.fomich.netdata.database.entity.ModuleType;
import lombok.Value;

import java.util.List;

@Value
public class ValidationErrorDTO {

    List<String> errors;

}
