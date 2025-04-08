package com.codelab.rest.webservices.trainingtool.exception;

public class CurrentCohortNotFoundException extends RuntimeException {
    public CurrentCohortNotFoundException(String message) {
        super(message);
    }
}
