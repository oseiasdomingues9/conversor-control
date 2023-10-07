package com.control.conversor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class CredentialException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CredentialException(String message) {
        super(message);
    }
}