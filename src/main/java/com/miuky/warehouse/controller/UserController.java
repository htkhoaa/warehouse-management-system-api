package com.miuky.warehouse.controller;

import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.service.iinterface.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
@ResponseStatus(HttpStatus.OK)
@Tag(name = "User Management", description = "Endpoints for managing staff profiles and administrative user lookups")
@SecurityRequirement(name = "Bearer")
public class UserController {
    private final IUserService service;

    @Operation(
            summary = "Get All Users (Admin Only)",
            description = "Retrieves a complete list of all staff members registered in the warehouse system. Requires ADMIN role."
    )
    @GetMapping("/admin/users")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> getAllUsers() {
        return service.getAllUsers();
    }

    @Operation(
            summary = "Get User by ID (Admin Only)",
            description = "Retrieves detailed information for a specific staff member using their unique ID. Requires ADMIN role."
    )
    @GetMapping("/admin/users/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> getUserById(
            @Parameter(description = "The unique internal ID of the user", example = "10")
            @PathVariable Long id
    ) {
        return service.getUserById(id);
    }

    @Operation(
            summary = "Get My Profile",
            description = "Retrieves the profile information of the currently authenticated staff member based on the provided JWT token."
    )
    @GetMapping("/profile")
    public ApiResponse<?> getMyProfile() {
        return service.getMyProfile();
    }
}