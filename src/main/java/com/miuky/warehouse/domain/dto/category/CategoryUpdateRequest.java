package com.miuky.warehouse.domain.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request body for updating an existing product category")
public record CategoryUpdateRequest(
        @Schema(description = "New name for the category", example = "Smart Home Appliance")
        String name,

        @Schema(description = "Updated description for the category", example = "Revised description for electronic devices")
        String description,

        @Schema(description = "The status of the category in the warehouse", example = "true")
        Boolean isActive
) {
}