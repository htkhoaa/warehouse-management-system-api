package com.miuky.warehouse.mapper.impl;

import com.miuky.warehouse.domain.dto.product.ProductUpdateRequest;
import com.miuky.warehouse.domain.entity.Category;
import com.miuky.warehouse.domain.entity.Product;
import com.miuky.warehouse.exception.AppException;
import com.miuky.warehouse.exception.ErrorCode;
import com.miuky.warehouse.mapper.iinterface.IProductMapper;
import com.miuky.warehouse.repository.CategoryRepository;
import com.miuky.warehouse.repository.ProductRepository;
import com.miuky.warehouse.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements IProductMapper {
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;

    @Override
    public void updateProductFromDto(ProductUpdateRequest dto, Product entity) {
        if (dto == null) return;
        if (dto.name() != null) entity.setName(dto.name());
        if (dto.price() != null) entity.setPrice(dto.price());
        if (dto.lowStockThreshold() != null) entity.setLowStockThreshold(dto.lowStockThreshold());
        if (dto.categoryId() != null) {
            Category category = categoryRepo.findById(dto.categoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            entity.setCategory(category);
        }
        if (dto.isActive() != null) entity.setActive(dto.isActive());
        entity.setModifiedBy(SecurityUtils.getUsername());
    }
}
