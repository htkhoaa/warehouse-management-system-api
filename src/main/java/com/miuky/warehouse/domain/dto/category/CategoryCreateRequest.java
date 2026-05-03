package com.miuky.warehouse.domain.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request body for creating a new product category")
public record CategoryCreateRequest(
        @Schema(description = "The unique name of the category", example = "Smart Home Appliances")
        @NotBlank(message = "Category name is required")
        String name,

        @Schema(description = "A brief description of what this category includes",
                example = "Intelligent devices designed to automate and enhance household tasks")
        @NotNull
        String description
) {
}