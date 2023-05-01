package com.ranggarifqi.moneymanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends CustomException{
    public UnauthorizedException(String message) {
        super(message);
        this.statusCode = HttpStatus.UNAUTHORIZED;
    }
}
