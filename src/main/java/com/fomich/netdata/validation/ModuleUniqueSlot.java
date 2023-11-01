package com.fomich.netdata.validation;


import com.fomich.netdata.validation.impl.ModuleUniqueSlotValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ModuleUniqueSlotValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleUniqueSlot {
    String message() default "This slot is already occupied by another module";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
