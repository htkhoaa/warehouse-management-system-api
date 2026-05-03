package com.miuky.warehouse.controller;

import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.service.iinterface.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
@ResponseStatus(HttpStatus.OK)
public class UserController {
    private final IUserService service;

    @GetMapping("/admin/users")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/admin/users/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @GetMapping("/profile")
    public ApiResponse<?> getMyProfile() {
        return service.getMyProfile();
    }
}
