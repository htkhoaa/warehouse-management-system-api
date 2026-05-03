package com.miuky.warehouse.service.impl;

import com.miuky.warehouse.domain.constant.TransactionType;
import com.miuky.warehouse.domain.dto.common.ApiResponse;
import com.miuky.warehouse.domain.dto.common.PageResponse;
import com.miuky.warehouse.domain.dto.transaction.InventoryExportRequest;
import com.miuky.warehouse.domain.dto.transaction.InventoryImportRequest;
import com.miuky.warehouse.domain.dto.transaction.TransactionResponse;
import com.miuky.warehouse.domain.entity.InventoryTransaction;
import com.miuky.warehouse.domain.entity.Product;
import com.miuky.warehouse.domain.entity.User;
import com.miuky.warehouse.exception.AppException;
import com.miuky.warehouse.exception.ErrorCode;
import com.miuky.warehouse.repository.InventoryTransactionRepository;
import com.miuky.warehouse.repository.ProductRepository;
import com.miuky.warehouse.security.CustomUserDetails;
import com.miuky.warehouse.service.iinterface.ITransactionService;
import com.miuky.warehouse.specification.TransactionSpecification;
import com.miuky.warehouse.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;

@Service @RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {
    private final InventoryTransactionRepository transactionRepo;
    private final ProductRepository productRepo;

    @Override @Transactional
    public ApiResponse<?> importInventory(InventoryImportRequest req) {
        Product productToImport = productRepo.findById(req.productId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        InventoryTransaction savedTrans = validateAndBuildAndSaveTransaction(
                productToImport, req.quantity(), req.reason(), TransactionType.IMPORT
        );

        productToImport.setCurrentQuantity(productToImport.getCurrentQuantity() + req.quantity());
        return ApiResponse.success(TransactionResponse.from(savedTrans), "Import successfully");
    }

    @Override @Transactional
    public ApiResponse<?> exportInventory(InventoryExportRequest req) {
        Product productToImport = productRepo.findById(req.productId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        InventoryTransaction savedTrans = validateAndBuildAndSaveTransaction(
                productToImport, req.quantity(), req.reason(), TransactionType.EXPORT);

        if (productToImport.getCurrentQuantity() < req.quantity()) throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
        productToImport.setCurrentQuantity(productToImport.getCurrentQuantity() - req.quantity());

        return ApiResponse.success(TransactionResponse.from(savedTrans), "Export successfully");
    }

    @Override
    public ApiResponse<?> getTransactions(int page, int size, String sort, String createdBy, Long productId, String type,
                                          LocalDate from, LocalDate to) {
        Sort s = sort.endsWith(",desc")
                ? Sort.by(sort.split(",")[0]).descending()
                : Sort.by(sort.split(",")[0]).ascending();
        Pageable pageable = PageRequest.of(page, size, s);

        var transSpec = TransactionSpecification.filterTransaction(createdBy, productId, type, from, to);
        Page<TransactionResponse> transRes = transactionRepo.findAll(transSpec, pageable).map(TransactionResponse::from);

        return ApiResponse.success(PageResponse.from(transRes));
    }

    @Override
    public ApiResponse<?> getMyTransactions(int page, int size) {
        String username = SecurityUtils.getUsername();
        return getTransactions(page, size, "createdAt, desc", username, null, null, null, null);
    }

    @Override
    public ApiResponse<?> getTransactionById(Long id) {
        String username = SecurityUtils.getUsername();
        InventoryTransaction res = transactionRepo.findByIdAndUsername(id, username)
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));
        return ApiResponse.success(TransactionResponse.from(res));
    }

    private InventoryTransaction validateAndBuildAndSaveTransaction(Product p, Integer quantity,
                                                                    String reason, TransactionType type) {
        if (!p.isActive()) throw new AppException(ErrorCode.INACTIVE_PRODUCT);

        User createdBy = SecurityUtils.getUser();
        InventoryTransaction newTrans = InventoryTransaction.builder().product(p).quantity(quantity)
                .reason(reason).user(createdBy).type(type).build();
        return transactionRepo.save(newTrans);
    }
}
