package com.miuky.warehouse.repository;

import com.miuky.warehouse.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
//    @Query("""
//           SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END
//           FROM Product p WHERE p.category.id = :categoryId
//    """)
    boolean existsByCategoryId(Long categoryId);

}
