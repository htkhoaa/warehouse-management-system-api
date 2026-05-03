package com.miuky.warehouse.controller;

import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.domain.dto.transaction.InventoryExportRequest;
import com.miuky.warehouse.domain.dto.transaction.InventoryImportRequest;
import com.miuky.warehouse.service.iinterface.ITransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
public class TransactionController {
    private final ITransactionService service;

    @PostMapping("/transactions/import")
    public ApiResponse<?> importInventory(@Valid @RequestBody InventoryImportRequest req) {
        return service.importInventory(req);
    }

    @PostMapping("/transactions/export")
    public ApiResponse<?> exportInventory(@Valid @RequestBody InventoryExportRequest req) {
        return service.exportInventory(req);
    }

    @GetMapping("/admin/transactions")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ApiResponse<?> getTransactions(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "createdAt,desc") String sort,
                                          @RequestParam(required = false) String createdBy,
                                          @RequestParam(required = false) Long productId,
                                          @RequestParam(required = false) String type,
                                          @RequestParam(required = false) LocalDate from,
                                          @RequestParam(required = false) LocalDate to) {
        return service.getTransactions(page, size, sort, createdBy, productId, type, from, to);
    }

    @GetMapping("/transactions")
    public ApiResponse<?> getMyTransactions(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        return service.getMyTransactions(page, size);
    }

    @GetMapping("/transactions/{id}")
    public ApiResponse<?> getTransactionById(@PathVariable Long id) {
        return service.getTransactionById(id);
    }
}
