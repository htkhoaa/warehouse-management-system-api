package com.miuky.warehouse.domain.dto.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductCreateRequest(
        @NotBlank(message = "Product name is required")
        @Size(max = 255, message = "Name must be under 255 characters")
        String name,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        BigDecimal price,

        @NotNull(message = "Current quantity is required")
        @Min(value = 0, message = "Quantity cannot be negative")
        Integer currentQuantity,

        @NotNull(message = "Low stock threshold is required")
        @Min(value = 0, message = "Threshold cannot be negative")
        Integer lowStockThreshold,

        @NotNull(message = "Category is required")
        Long categoryId
) {}
