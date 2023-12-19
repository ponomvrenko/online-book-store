package com.example.onlinebookstore.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMatch {
    String message() default "Repeated password differs from the password entered earlier";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
