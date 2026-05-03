package com.miuky.warehouse.domain.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Request body for updating existing product details")
public record ProductUpdateRequest(
        @Schema(description = "Updated product name", example = "Logitech MX Master 3S - Graphite")
        @Size(max = 255, message = "Name must be under 255 characters")
        String name,

        @Schema(description = "Updated category ID", example = "2")
        Long categoryId,

        @Schema(description = "Updated unit price", example = "89.50")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        BigDecimal price,

        @Schema(description = "Updated low-stock alert threshold", example = "30")
        @Min(value = 0, message = "Threshold cannot be negative")
        Integer lowStockThreshold,

        @Schema(description = "The availability status of the product in the warehouse", example = "true")
        Boolean isActive
) {
}
