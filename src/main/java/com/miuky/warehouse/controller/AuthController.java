package com.miuky.warehouse.controller;

import com.miuky.warehouse.domain.dto.auth.LoginRequest;
import com.miuky.warehouse.domain.dto.auth.RegisterRequest;
import com.miuky.warehouse.domain.dto.auth.PasswordChangeRequest;
import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.service.iinterface.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
@ResponseStatus(HttpStatus.OK)
@EnableMethodSecurity
@Tag(name = "Authentication & Authorization", description = "Endpoints for user login and administrative account management")
public class AuthController {
    private final IAuthService service;

    @Operation(
            summary = "User Login",
            description = "Authenticates user credentials and returns a JWT token for accessing warehouse resources."
    )
    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody LoginRequest req) {
        return service.login(req);
    }

    @Operation(
            summary = "Register Staff Account (Admin Only)",
            description = "Allows administrators to create new staff accounts. Requires ADMIN role.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @PostMapping("/admin/register")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> register(@Valid @RequestBody RegisterRequest req) {
        return service.register(req);
    }

    @Operation(
            summary = "Administrative Password Change",
            description = "Allows administrators to reset or change passwords for any account. Requires ADMIN role.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @PostMapping("/admin/password")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> changePassword(@Valid @RequestBody PasswordChangeRequest req) {
        return service.changePassword(req);
    }
}