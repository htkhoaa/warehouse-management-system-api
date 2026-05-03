package com.miuky.warehouse.domain.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryUpdateRequest(
        String name,
        String description,
        Boolean isActive
) {}
