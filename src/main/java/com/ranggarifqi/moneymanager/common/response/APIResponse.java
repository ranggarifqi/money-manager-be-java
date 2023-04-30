package com.ranggarifqi.moneymanager.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class APIResponse<T> {

    public static <T> ResponseEntity<APIResponse<T>> constructResponse(HttpStatus httpStatus, String message, T payload) {
        APIResponse<T> response = new APIResponse<T>(httpStatus.value(), message, payload);

        return ResponseEntity.status(httpStatus.value()).body(response);
    }

    public static <T> ResponseEntity<APIResponse<T>> constructResponse(String message, T payload) {
        APIResponse<T> response = new APIResponse<T>(message, payload);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public static <T> ResponseEntity<APIResponse<T>> constructResponse(T payload) {
        APIResponse<T> response = new APIResponse<T>(payload);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private final T payload;
    private int statusCode = HttpStatus.OK.value();

    private String message = "Success";

    public APIResponse(int statusCode, String message, T payload) {
        this.statusCode = statusCode;
        this.message = message;
        this.payload = payload;
    }

    public APIResponse(String message, T payload) {
        this.message = message;
        this.payload = payload;
    }

    public APIResponse(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
