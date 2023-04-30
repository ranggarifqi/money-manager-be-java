package com.ranggarifqi.moneymanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends CustomException{
    public InternalServerException(String message) {
        super(message);
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
