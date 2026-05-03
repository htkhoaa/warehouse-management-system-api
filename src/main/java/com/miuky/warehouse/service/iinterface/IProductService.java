package com.miuky.warehouse.service.iinterface;

import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.domain.dto.product.ProductCreateRequest;
import com.miuky.warehouse.domain.dto.product.ProductUpdateRequest;

public interface IProductService {
    ApiResponse<?> getAllProducts(int page, int size, String sort, String name, boolean isLowStock,
                                  Long categoryId, Integer minQty, Integer maxQty);

    ApiResponse<?> getProductById(Long id);

    ApiResponse<?> getLowStockProduct(int page, int size);

    ApiResponse<?> createProduct(ProductCreateRequest req);

    ApiResponse<?> updateProduct(Long id, ProductUpdateRequest req);

    ApiResponse<?> deleteProduct(Long id);
}
