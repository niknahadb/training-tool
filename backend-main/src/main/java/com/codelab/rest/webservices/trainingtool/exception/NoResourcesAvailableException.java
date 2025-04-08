package com.codelab.rest.webservices.trainingtool.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoResourcesAvailableException extends RuntimeException {
    private final String resourceName;

    public NoResourcesAvailableException(String resourceName) {
        super(String.format("No resources exist of type %s", resourceName)); // Post not found with id : 1
        this.resourceName = resourceName;
    }
}