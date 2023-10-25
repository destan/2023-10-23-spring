package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

//@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class BusinessLogicException extends ResponseStatusException {

    public BusinessLogicException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

    public BusinessLogicException(String message, Throwable cause) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message, cause);
    }
}
