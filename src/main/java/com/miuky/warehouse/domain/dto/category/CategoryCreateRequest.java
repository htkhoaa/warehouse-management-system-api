package com.miuky.warehouse.domain.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryCreateRequest(
        @NotBlank(message = "Category name is required")
        String name,

        @NotNull
        String description
) {}
