package com.miuky.warehouse.domain.dto.category;

import com.miuky.warehouse.domain.entity.Category;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        boolean isActive
) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.isActive());
    }
}
