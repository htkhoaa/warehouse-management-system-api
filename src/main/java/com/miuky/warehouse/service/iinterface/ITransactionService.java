package com.miuky.warehouse.service.iinterface;

import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.domain.dto.transaction.InventoryExportRequest;
import com.miuky.warehouse.domain.dto.transaction.InventoryImportRequest;

import java.time.LocalDate;

public interface ITransactionService {
    ApiResponse<?> importInventory(InventoryImportRequest req);

    ApiResponse<?> exportInventory(InventoryExportRequest req);

    ApiResponse<?> getTransactions(int page, int size, String sort, String createdBy, Long productId, String type, LocalDate from, LocalDate to);

    ApiResponse<?> getMyTransactions(int page, int size);

    ApiResponse<?> getTransactionById(Long id);
}
