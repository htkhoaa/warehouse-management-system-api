package com.miuky.warehouse.service.impl;

import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.domain.dto.common.PageResponse;
import com.miuky.warehouse.domain.dto.product.ProductCreateRequest;
import com.miuky.warehouse.domain.dto.product.ProductResponse;
import com.miuky.warehouse.domain.dto.product.ProductUpdateRequest;
import com.miuky.warehouse.domain.entity.Category;
import com.miuky.warehouse.domain.entity.Product;
import com.miuky.warehouse.exception.AppException;
import com.miuky.warehouse.exception.ErrorCode;
import com.miuky.warehouse.mapper.iinterface.IProductMapper;
import com.miuky.warehouse.repository.CategoryRepository;
import com.miuky.warehouse.repository.ProductRepository;
import com.miuky.warehouse.service.iinterface.IProductService;
import com.miuky.warehouse.specification.ProductSpecification;
import com.miuky.warehouse.util.AppUtils;
import com.miuky.warehouse.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final IProductMapper mapper;

    @Override
    public ApiResponse<?> getAllProducts(int page, int size, String sort, String name, boolean isLowStock, Long categoryId,
                                         Integer minQty, Integer maxQty) {
        Sort s = sort.endsWith(",desc")
                ? Sort.by(sort.split(",")[0]).descending()
                : Sort.by(sort.split(",")[0]).ascending();
        Pageable pageable = PageRequest.of(page, size, s);
        Specification<Product> productSpec = ProductSpecification.filterProduct(name, isLowStock, categoryId, minQty, maxQty);

        Page<ProductResponse> prodRes = productRepo.findAll(productSpec, pageable).map(ProductResponse::from);
        return ApiResponse.success(PageResponse.from(prodRes));
    }

    @Override
    public ApiResponse<?> getProductById(Long id) {
        Product p = productRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        if (!p.isActive()) throw new AppException(ErrorCode.INACTIVE_PRODUCT);
        return ApiResponse.success(ProductResponse.from(p));
    }

    @Override
    public ApiResponse<?> getLowStockProduct(int page, int size) {
        return getAllProducts(page, size, "currentQuantity,asc", null,
                true, null, null, null);
    }

    @Override
    @Transactional
    public ApiResponse<?> createProduct(ProductCreateRequest req) {
        Category currCategory = categoryRepo.findById(req.categoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Product newProduct = Product.builder().category(currCategory).name(req.name())
                .sku(AppUtils.createSku(req.name())).price(req.price()).currentQuantity(req.currentQuantity())
                .lowStockThreshold(req.lowStockThreshold()).createdBy(SecurityUtils.getUsername()).build();

        Product savedProduct = productRepo.save(newProduct);
        return ApiResponse.success(ProductResponse.from(savedProduct), "Create successfully");
    }

    @Override
    @Transactional
    public ApiResponse<?> updateProduct(Long id, ProductUpdateRequest req) {
        Product p = productRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        if (!p.isActive()) throw new AppException(ErrorCode.INACTIVE_PRODUCT);
        mapper.updateProductFromDto(req, p);
        return ApiResponse.success(ProductResponse.from(p), "Update successfully");
    }

    @Override
    @Transactional
    public ApiResponse<?> deleteProduct(Long id) {
        Product p = productRepo.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        p.setActive(false);
        p.setModifiedBy(SecurityUtils.getUsername());
        return ApiResponse.success(null, "Delete successfully");
    }


}
