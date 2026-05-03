package com.miuky.warehouse.service.iinterface;

import com.miuky.warehouse.domain.dto.category.CategoryCreateRequest;
import com.miuky.warehouse.domain.dto.category.CategoryUpdateRequest;
import com.miuky.warehouse.domain.dto.common.ApiResponse;

public interface ICategoryService {
    ApiResponse<?> getAllCategories();

    ApiResponse<?> createCategory(CategoryCreateRequest req);

    ApiResponse<?> updateCategory(Long id, CategoryUpdateRequest req);

    ApiResponse<?> deleteCategory(Long id);
}
