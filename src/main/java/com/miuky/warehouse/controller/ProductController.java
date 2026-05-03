package com.miuky.warehouse.controller;

import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.domain.dto.product.ProductCreateRequest;
import com.miuky.warehouse.domain.dto.product.ProductUpdateRequest;
import com.miuky.warehouse.service.iinterface.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
@Tag(name = "Product Management", description = "Endpoints for warehouse inventory control, stock monitoring, and product lifecycle")
public class ProductController {
    private final IProductService service;

    @Operation(
            summary = "Search and Filter Products",
            description = "Retrieves a paginated list of products with advanced filters for name, category, stock levels, and quantity ranges."
    )
    @GetMapping("/products")
    public ApiResponse<?> getAllProducts(
            @Parameter(description = "Page index (0..N)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Items per page", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sorting criteria: property,asc|desc", example = "currentQuantity,desc") @RequestParam(defaultValue = "currentQuantity,desc") String sort,
            @Parameter(description = "Filter by product name", example = "MacBook") @RequestParam(required = false) String name,
            @Parameter(description = "Quick filter for low stock items") @RequestParam(defaultValue = "false") boolean isLowStock,
            @Parameter(description = "Filter by category ID", example = "1") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Minimum quantity threshold", example = "5") @RequestParam(required = false) Integer minQty,
            @Parameter(description = "Maximum quantity threshold", example = "100") @RequestParam(required = false) Integer maxQty) {
        return service.getAllProducts(page, size, sort, name, isLowStock, categoryId, minQty, maxQty);
    }

    @Operation(summary = "Get Product Details", description = "Retrieves comprehensive information for a specific product by its ID.")
    @GetMapping("/products/{id}")
    public ApiResponse<?> getProductById(
            @Parameter(description = "Internal product ID", example = "101") @PathVariable Long id) {
        return service.getProductById(id);
    }

    @Operation(summary = "Get Low Stock Alerts", description = "Retrieves a specialized paginated list of products that have fallen below their minimum stock threshold.")
    @GetMapping("/products/low-stock")
    public ApiResponse<?> getLowStockProduct(
            @Parameter(description = "Page index", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Items per page", example = "10") @RequestParam(defaultValue = "10") int size) {
        return service.getLowStockProduct(page, size);
    }

    @Operation(
            summary = "Create Product (Admin Only)",
            description = "Adds a new product to the warehouse inventory. Requires ADMIN role.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @PostMapping("/admin/products/create")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> createProduct(@Valid @RequestBody ProductCreateRequest req) {
        return service.createProduct(req);
    }

    @Operation(
            summary = "Update Product (Admin Only)",
            description = "Updates existing product details including price, quantity, and category. Requires ADMIN role.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @PutMapping("/admin/products/update/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> updateProduct(
            @Parameter(description = "ID of the product to update", example = "101") @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest req) {
        return service.updateProduct(id, req);
    }

    @Operation(
            summary = "Delete Product (Admin Only)",
            description = "Permanently removes a product from the warehouse system. Requires ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/admin/products/delete/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> deleteProduct(
            @Parameter(description = "ID of the product to delete", example = "101") @PathVariable Long id) {
        return service.deleteProduct(id);
    }
}