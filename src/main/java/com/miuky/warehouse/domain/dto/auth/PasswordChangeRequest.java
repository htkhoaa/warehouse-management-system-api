package com.miuky.warehouse.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordChangeRequest(
        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Current password is required")
        String currentPassword,

        @Size(min = 8, max = 16, message = "Password at least have 8 chars and at most 16 chars")
        @NotBlank(message = "New password is required")
        String newPassword
) {}
