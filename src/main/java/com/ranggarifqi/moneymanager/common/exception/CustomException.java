package com.ranggarifqi.moneymanager.common.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{
    protected HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

    public CustomException(String message) {
        super(message);
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
