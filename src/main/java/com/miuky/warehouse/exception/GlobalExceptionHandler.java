package com.miuky.warehouse.exception;

import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.domain.dto.common.ErrorResponse;
import com.miuky.warehouse.domain.dto.common.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map((e) -> new ValidationError(e.getField(), e.getDefaultMessage())).toList();
        return ApiResponse.fail(errors);
    }

    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleAppException(AppException ex, HttpServletRequest req) {
        ErrorResponse res = ErrorResponse.build(ex.getErrorCode(), req);
        return ApiResponse.fail(res);
    }


    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleDataAccessException(DataAccessException ex, HttpServletRequest request) {
        ErrorCode errorCode = ErrorCode.DATABASE_ERROR;
        System.out.println(ex.getCause());
        if (ex.getCause() instanceof ConstraintViolationException) {
            errorCode = ErrorCode.DATA_CONFLICT;
        } else if (ex.getCause() instanceof DataException) {
            errorCode = ErrorCode.INVALID_QUERY_FIELD;
        }
        return ApiResponse.fail(ErrorResponse.build(errorCode, request));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<?> handleOtherException(HttpServletRequest request) {
        System.out.println("ALO");
        return ApiResponse.fail(ErrorResponse.build(ErrorCode.INTERNAL_SERVER_ERROR, request));
    }

}
