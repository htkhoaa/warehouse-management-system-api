package com.miuky.warehouse.mapper.iinterface;

import com.miuky.warehouse.domain.dto.product.ProductUpdateRequest;
import com.miuky.warehouse.domain.entity.Product;

public interface IProductMapper {
    void updateProductFromDto(ProductUpdateRequest dto, Product entity);
}
