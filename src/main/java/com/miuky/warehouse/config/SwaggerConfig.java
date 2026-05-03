package com.miuky.warehouse.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "Bearer";
        return new OpenAPI()
                .info(new Info()
                        .title("Warehouse Management System API")
                        .version("1.0.0")
                        .description("API documentation for the Warehouse Management System. " +
                                "Handles inventory, transactions, and user management.")
                        .contact(new Contact()
                                .name("Huỳnh Tuấn Khoa")
                                .email("huynhtuankhoa1007@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                        .servers(List.of(
                                new Server().url("http://localhost:8080").description("Local Development Server"),
                                new Server().url("https://api.warehouse.com").description("Production Server")
                        ))
                        .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                        .components(new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("Bearer")
                                                .bearerFormat("JWT")));
    }
}
