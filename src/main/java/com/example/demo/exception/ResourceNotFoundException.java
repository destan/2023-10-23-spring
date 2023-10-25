package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long resourceId, Class<?> resourceClass) {
        super(resourceClass.getSimpleName() + " with id " + resourceId + " is not found!");
    }
}
