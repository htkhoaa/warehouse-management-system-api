package com.miuky.warehouse.mapper.iinterface;

import com.miuky.warehouse.domain.dto.category.CategoryUpdateRequest;
import com.miuky.warehouse.domain.entity.Category;

public interface ICategoryMapper {
    void updateCategoryFromDto(CategoryUpdateRequest dto, Category entity);
}
