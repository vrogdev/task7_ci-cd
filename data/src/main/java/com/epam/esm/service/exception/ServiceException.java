package com.epam.esm.service.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

    private String message;
    private String code;
    private HttpStatus httpStatus;

    public ServiceException(String messageCode) {
        super(messageCode);
    }

    public ServiceException(String message, String code, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}