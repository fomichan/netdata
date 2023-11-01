package com.fomich.netdata.validation;


import com.fomich.netdata.validation.impl.SiteUniqueNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SiteUniqueNameValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SiteUniqueName {
    String message() default "This site name already in use";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
