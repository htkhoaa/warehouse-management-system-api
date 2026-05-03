package com.miuky.warehouse.controller;

import com.miuky.warehouse.domain.dto.category.CategoryCreateRequest;
import com.miuky.warehouse.domain.dto.category.CategoryUpdateRequest;
import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.service.iinterface.ICategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
@ResponseStatus(HttpStatus.OK)
@Tag(name = "Category Management", description = "Endpoints for managing warehouse product categories")
public class CategoryController {
    private final ICategoryService service;

    @Operation(
            summary = "Get All Categories",
            description = "Retrieves a list of all active product categories available in the warehouse."
    )
    @GetMapping("/categories")
    public ApiResponse<?> getAllCategories() {
        return service.getAllCategories();
    }

    @Operation(
            summary = "Create New Category (Admin Only)",
            description = "Allows administrators to create a new product category. Requires ADMIN role.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @PostMapping("/admin/categories/create")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> createCategory(@Valid @RequestBody CategoryCreateRequest req) {
        return service.createCategory(req);
    }

    @Operation(
            summary = "Update Category (Admin Only)",
            description = "Updates the details of an existing category by its unique ID. Requires ADMIN role.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @PostMapping("/admin/categories/update/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> updateCategory(
            @Parameter(description = "The unique ID of the category to be updated", example = "5")
            @PathVariable Long id,
            @Valid @RequestBody CategoryUpdateRequest req
    ) {
        return service.updateCategory(id, req);
    }

    @Operation(
            summary = "Delete Category (Admin Only)",
            description = "Removes a category from the system. Note: This may fail if the category is currently linked to existing products. Requires ADMIN role.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @DeleteMapping("/admin/categories/delete/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> deleteCategory(
            @Parameter(description = "The unique ID of the category to be deleted", example = "5")
            @PathVariable Long id
    ) {
        return service.deleteCategory(id);
    }
}