package com.miuky.warehouse.controller;

import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.domain.dto.transaction.InventoryExportRequest;
import com.miuky.warehouse.domain.dto.transaction.InventoryImportRequest;
import com.miuky.warehouse.service.iinterface.ITransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
@Tag(name = "Inventory Transactions", description = "Endpoints for managing stock movements, including imports, exports, and history tracking")
@SecurityRequirement(name = "Bearer")
public class TransactionController {
    private final ITransactionService service;

    @Operation(summary = "Import Inventory", description = "Records a stock-in transaction when new goods arrive at the warehouse.")
    @PostMapping("/transactions/import")
    public ApiResponse<?> importInventory(@Valid @RequestBody InventoryImportRequest req) {
        return service.importInventory(req);
    }

    @Operation(summary = "Export Inventory", description = "Records a stock-out transaction when goods are shipped or removed from the warehouse.")
    @PostMapping("/transactions/export")
    public ApiResponse<?> exportInventory(@Valid @RequestBody InventoryExportRequest req) {
        return service.exportInventory(req);
    }

    @Operation(
            summary = "Search All Transactions (Admin Only)",
            description = "Retrieves a paginated list of all system transactions with advanced filters for audit purposes. Requires ADMIN role."
    )
    @GetMapping("/admin/transactions")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> getTransactions(
            @Parameter(description = "Page index", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Items per page", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort property and direction", example = "createdAt,desc") @RequestParam(defaultValue = "createdAt,desc") String sort,
            @Parameter(description = "Filter by staff username") @RequestParam(required = false) String createdBy,
            @Parameter(description = "Filter by specific product ID", example = "101") @RequestParam(required = false) Long productId,
            @Parameter(description = "Transaction type (IMPORT/EXPORT)", example = "IMPORT") @RequestParam(required = false) String type,
            @Parameter(description = "Start date (YYYY-MM-DD)", example = "2024-01-01") @RequestParam(required = false) LocalDate from,
            @Parameter(description = "End date (YYYY-MM-DD)", example = "2024-12-31") @RequestParam(required = false) LocalDate to) {
        return service.getTransactions(page, size, sort, createdBy, productId, type, from, to);
    }

    @Operation(summary = "Get My Transaction History", description = "Retrieves a paginated list of transactions performed by the currently authenticated staff member.")
    @GetMapping("/transactions")
    public ApiResponse<?> getMyTransactions(
            @Parameter(description = "Page index", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Items per page", example = "10") @RequestParam(defaultValue = "10") int size) {
        return service.getMyTransactions(page, size);
    }

    @Operation(summary = "Get Transaction Details", description = "Retrieves specific information about a single transaction by its unique ID.")
    @GetMapping("/transactions/{id}")
    public ApiResponse<?> getTransactionById(
            @Parameter(description = "Internal transaction ID", example = "5001") @PathVariable Long id) {
        return service.getTransactionById(id);
    }
}