package com.miuky.warehouse.controller;

import com.miuky.warehouse.domain.dto.category.CategoryCreateRequest;
import com.miuky.warehouse.domain.dto.category.CategoryUpdateRequest;
import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.service.iinterface.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
@ResponseStatus(HttpStatus.OK)
public class CategoryController {
    private final ICategoryService service;

    @GetMapping("/categories")
    public ApiResponse<?> getAllCategories() {
        return service.getAllCategories();
    }

    @PostMapping("/admin/categories/create")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> createCategory(@Valid @RequestBody CategoryCreateRequest req) {
        return service.createCategory(req);
    }

    @PostMapping("/admin/categories/update/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryUpdateRequest req) {
        return service.updateCategory(id, req);
    }

    @DeleteMapping("/admin/categories/delete/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> deleteCategory(@PathVariable Long id) {
        return service.deleteCategory(id);
    }
}
