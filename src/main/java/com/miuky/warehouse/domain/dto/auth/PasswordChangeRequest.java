package com.miuky.warehouse.domain.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for administrative password updates")
public record PasswordChangeRequest(
        @Schema(description = "Target username whose password will be changed", example = "staff_khoa")
        @NotBlank(message = "Username is required")
        String username,

        @Schema(description = "Current password for verification", example = "khoa123456")
        @NotBlank(message = "Current password is required")
        String currentPassword,

        @Schema(description = "New secure password for the account", example = "khoa12345678")
        @Size(min = 8, max = 16, message = "Password must be between 8 and 16 characters")
        @NotBlank(message = "New password is required")
        String newPassword
) {
}
