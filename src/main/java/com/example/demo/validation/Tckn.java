package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TcknValidator.class)
public @interface Tckn {
    String message() default "{tckn}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
