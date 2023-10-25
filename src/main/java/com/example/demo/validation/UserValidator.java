package com.example.demo.validation;

import com.example.demo.user.User;
import com.example.demo.user.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz) || UserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (target instanceof User user) {
            if (user.getEmail().endsWith("example.com") && user.getFullName().contains("john")) {
                var errorArgs = new String[]{String.valueOf(user.getEmail())};
                errors.rejectValue("email", "email.example.john", errorArgs, "Email cannot be example.com for John!");
            }
        }

        if (target instanceof UserDto userDto) {
            // ...
        }

    }
}