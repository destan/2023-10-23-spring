package com.example.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TcknValidator implements ConstraintValidator<Tckn, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value.length() != 11) {
            return false;
        }

        try {
            Long.parseLong(value);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
