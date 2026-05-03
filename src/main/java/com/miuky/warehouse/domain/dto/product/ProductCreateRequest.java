package com.miuky.warehouse.domain.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Request body for creating a new product in the warehouse")
public record ProductCreateRequest(
        @Schema(description = "The official name of the product", example = "Robot Vacuum Cleaner")
        @NotBlank(message = "Product name is required")
        @Size(max = 255, message = "Name must be under 255 characters")
        String name,

        @Schema(description = "Unit price of the product", example = "450.00")
        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        BigDecimal price,

        @Schema(description = "Initial stock quantity available in the warehouse", example = "25")
        @NotNull(message = "Current quantity is required")
        @Min(value = 0, message = "Quantity cannot be negative")
        Integer currentQuantity,

        @Schema(description = "The minimum stock level before a low-stock alert is triggered", example = "5")
        @NotNull(message = "Low stock threshold is required")
        @Min(value = 0, message = "Threshold cannot be negative")
        Integer lowStockThreshold,

        @Schema(description = "The ID of the category this product belongs to", example = "4")
        @NotNull(message = "Category is required")
        Long categoryId
) {
}
