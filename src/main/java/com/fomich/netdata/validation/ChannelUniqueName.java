package com.fomich.netdata.validation;


import com.fomich.netdata.validation.impl.ChannelUniqueNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ChannelUniqueNameValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChannelUniqueName {
    String message() default "This channel name already in use";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
