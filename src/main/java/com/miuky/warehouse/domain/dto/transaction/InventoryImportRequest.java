package com.miuky.warehouse.domain.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for recording a stock-in (import) transaction")
public record InventoryImportRequest(
        @Schema(description = "The unique ID of the product being imported", example = "3")
        @NotNull(message = "Product is required")
        Long productId,

        @Schema(description = "The number of units to add to the inventory", example = "6")
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity,

        @Schema(description = "The justification or source of the import", example = "Import 6 Sony WH-1000XM5 from Supplier A")

        @NotBlank(message = "Reason is required")
        String reason
) {
}
