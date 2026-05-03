package com.miuky.warehouse.mapper.impl;

import com.miuky.warehouse.domain.dto.category.CategoryUpdateRequest;
import com.miuky.warehouse.domain.entity.Category;
import com.miuky.warehouse.mapper.iinterface.ICategoryMapper;
import com.miuky.warehouse.repository.CategoryRepository;
import com.miuky.warehouse.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapperImpl implements ICategoryMapper {
    private final CategoryRepository categoryRepo;

    @Override
    public void updateCategoryFromDto(CategoryUpdateRequest dto, Category entity) {
        if (dto.name() != null) entity.setName(dto.name());
        if (dto.description() != null) entity.setDescription(dto.description());
        if (dto.isActive() != null) entity.setActive(dto.isActive());

        entity.setModifiedBy(SecurityUtils.getUsername());
    }
}
