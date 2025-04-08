package com.codelab.rest.webservices.trainingtool.exception;

public class FileUploadException extends SpringBootFileUploadException{

    public FileUploadException(String message) {
        super(message);
    }
}