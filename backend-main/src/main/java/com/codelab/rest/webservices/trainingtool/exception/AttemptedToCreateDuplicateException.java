package com.codelab.rest.webservices.trainingtool.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AttemptedToCreateDuplicateException extends RuntimeException {
    private final String resourceName;

    public AttemptedToCreateDuplicateException(String resourceName) {
        super(String.format("Attempted to create a duplicate %s which is not allowed", resourceName)); // Post not found with id : 1
        this.resourceName = resourceName;
    }
}

