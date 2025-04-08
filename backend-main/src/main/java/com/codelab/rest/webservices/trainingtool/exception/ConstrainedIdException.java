package com.codelab.rest.webservices.trainingtool.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ConstrainedIdException extends RuntimeException {
    private final String message;

    public ConstrainedIdException(String message) {
        super(String.format("Constrained ids violated: %s", message));
        this.message = message;
    }
}
