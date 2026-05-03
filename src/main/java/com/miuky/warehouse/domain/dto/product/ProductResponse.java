package com.miuky.warehouse.domain.dto.product;


import com.miuky.warehouse.domain.entity.Product;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductResponse(
        Long id,
        String name,
        String sku,
        BigDecimal price,
        Integer currentQuantity,
        Integer lowStockThreshold,
        String categoryName,
        boolean isActive,
        String createdBy,
        Instant createdAt,
        String lastModifiedBy,
        Instant lastModifiedAt
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSku(),
                product.getPrice(),
                product.getCurrentQuantity(),
                product.getLowStockThreshold(),
                product.getCategory().getName(),
                product.isActive(),
                product.getCreatedBy(),
                product.getCreatedAt(),
                product.getModifiedBy(),
                product.getModifiedAt()
        );
    }
}
