package com.control.conversor.exception;

import com.control.conversor.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler (value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseDTO handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return new ResponseDTO(
                ex.getMessage(),
                HttpStatus.FORBIDDEN.value(),
                null,
                Instant.now().toString(),
                request.getDescription(false)
        );
    }
}
