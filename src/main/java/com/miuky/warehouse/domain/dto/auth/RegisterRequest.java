package com.miuky.warehouse.domain.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for creating a new staff account")
public record RegisterRequest(
        @Schema(description = "Unique username for the staff member", example = "staff_khoa")
        @NotBlank(message = "Username is required")
        @Size(max = 50, message = "Username must be at most 50 characters")
        String username,

        @Schema(description = "Full name of the employee", example = "Huỳnh Tuấn Khoa")
        @NotBlank(message = "Full name is required")
        String fullName,

        @Schema(description = "Initial password for the account (8-16 characters)", example = "khoa123456")
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
        String password
) {
}