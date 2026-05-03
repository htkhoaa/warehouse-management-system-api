package com.miuky.warehouse.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Username is required")
        @Size(max = 50, message = "Username at most 50 characters")
        String username,

        @NotBlank(message = "Full name is required")
        String fullName,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 16, message = "Password at least have 8 chars and at most 16 chars")
        String password
) {}
