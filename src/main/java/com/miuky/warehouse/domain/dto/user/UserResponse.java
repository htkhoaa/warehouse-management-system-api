package com.miuky.warehouse.domain.dto.user;

import com.miuky.warehouse.domain.entity.User;

import java.time.Instant;

public record UserResponse(
        String username,
        String fullName,
        String role,
        Instant createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getUsername(),
                user.getFullName(),
                user.getRole().getName(),
                user.getCreatedAt()
        );
    }
}
