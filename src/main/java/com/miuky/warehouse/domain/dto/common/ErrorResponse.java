package com.miuky.warehouse.domain.dto.common;

import com.miuky.warehouse.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import java.time.Instant;

@Builder
public record ErrorResponse(
        Instant timestamp,
        int code,
        String message,
        String status,
        String path
) {
    public static ErrorResponse build(ErrorCode errorCode, HttpServletRequest request) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .status(errorCode.getStatusCode().toString())
                .path(request.getRequestURI())
                .build();
    }

    public static ErrorResponse build(String message, String status, HttpServletRequest request) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .code(400)
                .message(message)
                .status(status)
                .path(request.getRequestURI())
                .build();
    }
}

