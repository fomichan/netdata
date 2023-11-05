package com.fomich.netdata.validation;


import com.fomich.netdata.validation.impl.MuxUniqueNameValidator;
import com.fomich.netdata.validation.impl.UserUniqueNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserUniqueNameValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserUniqueName {
    String message() default "This user name already in use";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
