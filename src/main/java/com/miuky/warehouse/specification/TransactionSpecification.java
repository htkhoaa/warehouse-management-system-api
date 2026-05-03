package com.miuky.warehouse.specification;

import com.miuky.warehouse.domain.entity.InventoryTransaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {

    public static Specification<InventoryTransaction> filterTransaction(String createdBy, Long productId, String type, LocalDate startDate, LocalDate endDate) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            ZoneId zone = ZoneId.systemDefault();

            if (createdBy != null) predicates.add(cb.equal(root.get("user").get("username"), createdBy));
            if (productId != null) predicates.add(cb.equal(root.get("product").get("id"), productId));
            if (type != null) predicates.add(cb.equal(cb.lower(root.get("type")), type));
            if (startDate != null) {
                Instant startInstant = startDate.atStartOfDay(zone).toInstant();
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), startInstant));
            }
            if (endDate != null) {
                Instant endInstant = endDate.atTime(LocalTime.MAX).atZone(zone).toInstant();
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), endInstant));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
