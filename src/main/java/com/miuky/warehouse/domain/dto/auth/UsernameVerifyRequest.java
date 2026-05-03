package com.miuky.warehouse.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record UsernameVerifyRequest(
        @NotBlank(message = "Username is required") String username ){}
