package com.miuky.warehouse.domain.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body for user authentication")
public record LoginRequest(
        @Schema(description = "Account username", example = "admin")
        @NotBlank(message = "Username is required")
        String username,

        @Schema(description = "Account password", example = "admin123")
        @NotBlank(message = "Password is required")
        String password
) {
}
