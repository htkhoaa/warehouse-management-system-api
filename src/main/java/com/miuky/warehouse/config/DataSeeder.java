package com.miuky.warehouse.config;

import com.miuky.warehouse.domain.entity.Category;
import com.miuky.warehouse.domain.entity.Product;
import com.miuky.warehouse.domain.entity.Role;
import com.miuky.warehouse.domain.entity.User;
import com.miuky.warehouse.exception.AppException;
import com.miuky.warehouse.exception.ErrorCode;
import com.miuky.warehouse.repository.CategoryRepository;
import com.miuky.warehouse.repository.ProductRepository;
import com.miuky.warehouse.repository.RoleRepository;
import com.miuky.warehouse.repository.UserRepository;
import com.miuky.warehouse.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepo;
    private final CategoryRepository categoryRepo;
    private final ProductRepository productRepo;

    @Override
    public void run(String... args) throws Exception {
        seedUser();
        if (categoryRepo.count() == 0) {
            seedData();
        }
        System.out.println(">> Database seeding completed successfully!");
    }

    private void seedData() {
        Category electronics = Category.builder()
                .name("Electronics").description("Gadgets and electronic devices")
                .isActive(true).createdBy("admin").build();

        Category furniture = Category.builder()
                .name("Furniture").description("Home and office furniture")
                .isActive(true).createdBy("admin").build();

        Category accessories = Category.builder()
                .name("Accessories").description("Computer and mobile accessories")
                .isActive(true).createdBy("admin").build();

        categoryRepo.saveAll(List.of(electronics, furniture, accessories));

        List<Product> products = List.of(
                Product.builder()
                        .name("iPhone 15 Pro Max").sku(AppUtils.createSku("iPhone 15 Pro Max")).price(new BigDecimal("1199.00"))
                        .currentQuantity(50).lowStockThreshold(5).category(electronics)
                        .isActive(true).createdBy("admin").build(),

                Product.builder()
                        .name("Samsung S24 Ultra").sku(AppUtils.createSku("Samsung S24 Ultra")).price(new BigDecimal("1299.00"))
                        .currentQuantity(3).lowStockThreshold(10).category(electronics)
                        .isActive(true).createdBy("admin").build(),

                Product.builder()
                        .name("Sony WH-1000XM5").sku(AppUtils.createSku("Sony WH-1000XM5")).price(new BigDecimal("399.00"))
                        .currentQuantity(0).lowStockThreshold(5).category(accessories)
                        .isActive(true).createdBy("admin").build(),

                Product.builder()
                        .name("Ergonomic Chair").sku(AppUtils.createSku("Ergonomic Chair")).price(new BigDecimal("250.00"))
                        .currentQuantity(15).lowStockThreshold(2).category(furniture)
                        .isActive(true).createdBy("admin").build()
        );

        productRepo.saveAll(products);
    }

    private void seedUser() {
        if (roleRepo.count() == 0) {
            Role adminRole = new Role("ADMIN");
            Role staffRole = new Role("STAFF");
            roleRepo.save(adminRole);
            roleRepo.save(staffRole);
        }

        if (!userRepo.existsByUsername("admin")) {
            Role adminRole = roleRepo.findByName("ADMIN")
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123"));
            admin.setFullName("System Administrator");
            admin.setRole(adminRole);
            userRepo.save(admin);
        }

    }
}
