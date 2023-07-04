package com.epam.esm.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class AccessDeniedException extends AuthenticationException {
    private final HttpStatus httpStatus;

    public AccessDeniedException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }
}
