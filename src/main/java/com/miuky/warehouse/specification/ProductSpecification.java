package com.miuky.warehouse.specification;

import com.miuky.warehouse.domain.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> filterProduct(String name, boolean isLowStock, Long categoryId,
                                                       Integer minQty, Integer maxQty) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }
            if (minQty != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("currentQuantity"), minQty));
            }
            if (maxQty != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("currentQuantity"), maxQty));
            }
            if (isLowStock) {
                predicates.add(cb.lessThan(root.get("currentQuantity"), root.get("lowStockThreshold")));
                predicates.add(cb.equal(root.get("isActive"), true));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
