package com.miuky.warehouse.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Table(name = "categories")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Category extends BaseEntity {

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "is_active")
    @Builder.Default
    private boolean isActive = true;

    @Column(columnDefinition = "TEXT")
    private String description;
}
