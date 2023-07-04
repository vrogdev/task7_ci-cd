package com.epam.esm.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtAuthorizationException extends AccessDeniedException {
    public JwtAuthorizationException(String msg) {
        super(msg, HttpStatus.FORBIDDEN);
    }
}
