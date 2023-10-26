package com.example.demo;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public CustomError conflict(SQLIntegrityConstraintViolationException e) {
        //TODO custom error reason
        log.error("Global Hata mesaji: " + e.getMessage(), e);

        //UK_1J9D9A06I600GD43UU3KM82JW_INDEX_7 user
        //UK_2JM25HJRQ6IV4W8Y1DHI0D9P4_INDEX_2 post

        final String message;
        if (e.getMessage().contains("UK_1J9D9A06I600GD43UU3KM82JW_INDEX_7")) {
            message = "User email already exists!";
        }
        else if (e.getMessage().contains("UK_2JM25HJRQ6IV4W8Y1DHI0D9P4_INDEX_2")) {
            message = "Post title already exists!";
        }
        else {
            message = "Resource already exists!";
        }

        return new CustomError(message);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public CustomError constraint(ConstraintViolationException e) {
        log.error("Global Hata mesaji: " + e.getMessage(), e);
        return new CustomError(e.getMessage());
    }

}

record CustomError(String message) {

}