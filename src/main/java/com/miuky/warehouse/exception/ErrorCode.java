package com.miuky.warehouse.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    INVALID_TOKEN(1, "INVALID_TOKEN", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(2, "USER_NOT_FOUND", HttpStatus.NOT_FOUND),
    USERNAME_ALREADY_EXISTED(3, "USERNAME_ALREADY_EXISTED", HttpStatus.CONFLICT),
    INVALID_CREDENTIALS(4, "INVALID_CREDENTIALS", HttpStatus.UNAUTHORIZED),
    CATEGORY_NAME_ALREADY_EXISTED(5, "CATEGORY_NAME_ALREADY_EXISTED", HttpStatus.CONFLICT),
    CATEGORY_NOT_FOUND(6, "CATEGORY_NOT_FOUND", HttpStatus.NOT_FOUND),
    CATEGORY_CAN_BE_DELETED(7, "CATEGORY_CAN_BE_DELETED", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(8, "PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND),
    INSUFFICIENT_STOCK(9, "INSUFFICIENT_STOCK", HttpStatus.BAD_REQUEST),
    USER_UNAUTHORIZED(10, "USER_UNAUTHORIZED", HttpStatus.UNAUTHORIZED),
    TRANSACTION_NOT_FOUND(11, "TRANSACTION_NOT_FOUND", HttpStatus.NOT_FOUND),
    DATABASE_ERROR(12, "DATABASE_ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
    DATA_CONFLICT(13, "DATA_CONFLICT", HttpStatus.CONFLICT),
    INVALID_QUERY_FIELD(14, "INVALID_QUERY_FIELD", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(15, "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
    ROLE_NOT_FOUND(16, "ROLE_NOT_FOUND", HttpStatus.NOT_FOUND),
    FORBIDDEN(17, "FORBIDDEN", HttpStatus.FORBIDDEN),
    INACTIVE_PRODUCT(18, "INACTIVE_PRODUCT", HttpStatus.NOT_FOUND);

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
