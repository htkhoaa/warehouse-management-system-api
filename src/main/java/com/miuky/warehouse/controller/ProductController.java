package com.miuky.warehouse.controller;

import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.domain.dto.product.ProductCreateRequest;
import com.miuky.warehouse.domain.dto.product.ProductUpdateRequest;
import com.miuky.warehouse.service.iinterface.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class ProductController {
    private final IProductService service;

    @GetMapping("/products")
    public ApiResponse<?> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "currentQuantity,desc") String sort,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(defaultValue = "false") boolean isLowStock,
                                         @RequestParam(required = false) Long categoryId,
                                         @RequestParam(required = false) Integer minQty,
                                         @RequestParam(required = false) Integer maxQty) {
        return service.getAllProducts(page, size, sort, name, isLowStock, categoryId, minQty, maxQty);
    }

    @GetMapping("/products/{id}")
    public ApiResponse<?> getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @GetMapping("/products/low-stock")
    public ApiResponse<?> getLowStockProduct(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return service.getLowStockProduct(page, size);
    }

    @PostMapping("/admin/products/create")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> createProduct(@Valid @RequestBody ProductCreateRequest req) {
        return service.createProduct(req);
    }

    @PutMapping("/admin/products/update/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest req) {
        return service.updateProduct(id, req);
    }

    @DeleteMapping("/admin/products/delete/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> deleteProduct(@PathVariable Long id) {
        return service.deleteProduct(id);
    }
}
