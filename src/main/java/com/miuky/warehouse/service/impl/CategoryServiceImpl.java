package com.miuky.warehouse.service.impl;

import com.miuky.warehouse.domain.dto.category.CategoryCreateRequest;
import com.miuky.warehouse.domain.dto.category.CategoryResponse;
import com.miuky.warehouse.domain.dto.category.CategoryUpdateRequest;
import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.domain.entity.Category;
import com.miuky.warehouse.exception.AppException;
import com.miuky.warehouse.exception.ErrorCode;
import com.miuky.warehouse.mapper.iinterface.ICategoryMapper;
import com.miuky.warehouse.repository.CategoryRepository;
import com.miuky.warehouse.repository.ProductRepository;
import com.miuky.warehouse.service.iinterface.ICategoryService;
import com.miuky.warehouse.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;
    private final ICategoryMapper mapper;

    public ApiResponse<?> getAllCategories() {
        List<CategoryResponse> res = categoryRepo.findAll().stream().map(CategoryResponse::from).toList();
        return ApiResponse.success(res);
    }

    public ApiResponse<?> createCategory(CategoryCreateRequest req) {
        if (categoryRepo.existsByName(req.name())) throw new AppException(ErrorCode.CATEGORY_NAME_ALREADY_EXISTED);

        Category newCategory = Category.builder().name(req.name()).createdBy(SecurityUtils.getUsername())
                .description(req.description()).build();

        Category savedCategory = categoryRepo.save(newCategory);

        return ApiResponse.success(CategoryResponse.from(savedCategory), "Create successfully");
    }

    @Transactional @Override
    public ApiResponse<?> updateCategory(Long id, CategoryUpdateRequest req) {
        Category currCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        mapper.updateCategoryFromDto(req, currCategory);

        return ApiResponse.success(CategoryResponse.from(currCategory), "Update successfully");
    }

    @Transactional @Override
    public ApiResponse<?> deleteCategory(Long id) {
        Category currCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        if (productRepo.existsByCategoryId(id)) throw new AppException(ErrorCode.CATEGORY_CAN_BE_DELETED);

        currCategory.setActive(false);
        currCategory.setModifiedBy(SecurityUtils.getUsername());

        return ApiResponse.success(null, "Delete successfully");
    }
}
