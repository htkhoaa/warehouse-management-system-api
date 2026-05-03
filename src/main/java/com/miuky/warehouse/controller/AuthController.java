package com.miuky.warehouse.controller;

import com.miuky.warehouse.domain.dto.auth.LoginRequest;
import com.miuky.warehouse.domain.dto.auth.PasswordChangeRequest;
import com.miuky.warehouse.domain.dto.auth.RegisterRequest;
import com.miuky.warehouse.domain.dto.auth.UsernameVerifyRequest;
import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.service.iinterface.IAuthService;
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
public class AuthController {
    private final IAuthService service;

    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody LoginRequest req) {
        return service.login(req);
    }

    @PostMapping("/admin/register")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> register(@Valid @RequestBody RegisterRequest req) {
        return service.register(req);
    }

    @PostMapping("/admin/password")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> changePassword(@Valid @RequestBody PasswordChangeRequest req) {
        return service.changePassword(req);
    }

}
