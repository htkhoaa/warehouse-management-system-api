package com.miuky.warehouse.domain.dto.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        @Size(max = 255, message = "Name must be under 255 characters")
        String name,

        Long categoryId,

        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        BigDecimal price,

        @Min(value = 0, message = "Threshold cannot be negative")
        Integer lowStockThreshold,

        Boolean isActive
) {}
