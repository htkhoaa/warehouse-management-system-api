package com.miuky.warehouse.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    DATABASE_ERROR(1000, "Database connection or execution failed.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_QUERY_FIELD(1001, "The requested query field is invalid.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(1002, "An unexpected error occurred on the server.", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(2000, "User authentication failed.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN(2001, "Token is invalid or has expired.", HttpStatus.UNAUTHORIZED),
    INVALID_CREDENTIALS(2002, "Invalid username or password.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(2003, "You do not have permission to access this resource.", HttpStatus.FORBIDDEN),
    USER_NOT_FOUND(3000, "User could not be found.", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(3001, "Username is already taken.", HttpStatus.CONFLICT),
    ROLE_NOT_FOUND(3002, "Role could not be found.", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(4000, "Category could not be found.", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(4001, "Category name already exists.", HttpStatus.CONFLICT),
    CATEGORY_CANNOT_BE_DELETED(4002, "Category cannot be deleted because it contains products.", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(5000, "Product could not be found.", HttpStatus.NOT_FOUND),
    INACTIVE_PRODUCT(5001, "Product is currently inactive.", HttpStatus.BAD_REQUEST),
    INSUFFICIENT_STOCK(5002, "Insufficient stock quantity in warehouse.", HttpStatus.BAD_REQUEST),
    DATA_CONFLICT(5003, "Data conflict occurred during processing.", HttpStatus.CONFLICT),
    TRANSACTION_NOT_FOUND(6000, "Transaction record could not be found.", HttpStatus.NOT_FOUND);

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
