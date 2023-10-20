package com.fomich.netdata.validation;


import com.fomich.netdata.validation.impl.MuxChannelValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MuxChannelValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MuxChannel {
    String message() default "This channel already passes through the multiplexer";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
