package com.fomich.netdata.validation;


import com.fomich.netdata.validation.impl.MuxUniqueNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MuxUniqueNameValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MuxUniqueName {
    String message() default "This multiplexer name already in use";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
