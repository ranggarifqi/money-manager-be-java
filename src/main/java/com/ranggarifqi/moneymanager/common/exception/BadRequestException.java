package com.ranggarifqi.moneymanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends CustomException{
    public BadRequestException(String message) {
        super(message);
        this.statusCode = HttpStatus.BAD_REQUEST;
    }
}
