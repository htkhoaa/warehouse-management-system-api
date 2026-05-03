package com.miuky.warehouse.repository;

import com.miuky.warehouse.domain.entity.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long>,
        JpaSpecificationExecutor<InventoryTransaction> {

    @Query("""
           SELECT it FROM InventoryTransaction it
           JOIN FETCH it.user u
           JOIN FETCH it.product p
           WHERE it.id = :id 
           AND (u.username = :username OR u.username = 'admin')
    """)
    Optional<InventoryTransaction> findByIdAndUsername(Long id, String username);
}
