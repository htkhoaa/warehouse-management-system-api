package com.miuky.warehouse.domain.dto.transaction;

import com.miuky.warehouse.domain.dto.product.ProductResponse;
import com.miuky.warehouse.domain.entity.InventoryTransaction;

import java.time.Instant;

public record TransactionResponse(
        Long id,
        ProductResponse prodRes,
        String createdBy,
        String type,
        Integer quantity,
        String reason,
        Instant createdAt
) {
    public static TransactionResponse from(InventoryTransaction it) {
        return new TransactionResponse(
                it.getId(),
                ProductResponse.from(it.getProduct()),
                it.getUser().getFullName(),
                it.getType().name(),
                it.getQuantity(),
                it.getReason(),
                it.getCreatedAt()
        );
    }
}
