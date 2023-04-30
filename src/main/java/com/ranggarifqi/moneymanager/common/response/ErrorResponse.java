package com.ranggarifqi.moneymanager.common.response;

import com.ranggarifqi.moneymanager.common.exception.BadRequestException;
import com.ranggarifqi.moneymanager.common.exception.ConflictException;
import com.ranggarifqi.moneymanager.common.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ErrorResponse {
    public static ResponseStatusException construct(Exception e) {
        if (e instanceof CustomException) {
            return new ResponseStatusException(((CustomException) e).getStatusCode(), e.getMessage(), e);
        }
        return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
    }
}
