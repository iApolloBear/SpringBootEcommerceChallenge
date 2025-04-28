package com.aldo.ecommerce_challenge.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI ecommerceOpenAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("E-commerce API")
                .description(
                    """
                Welcome to the **E-commerce API**.

                This API allows you to manage products, orders, and order items in an online store environment.

                ## ✨ Features
                - Manage Products: Create, update, delete, and list products with pricing and descriptions.
                - Manage Orders: Create and update orders, automatically calculate total prices based on associated order items.
                - Manage Order Items: Link products to orders, update quantities, and handle dynamic price totals.
                - Validation: Enforced request validations on all input data with meaningful error responses.
                - Error Handling: Standardized error responses for bad requests, not found, and server errors.
                - OpenAPI 3 Documentation: All endpoints fully documented with examples, tags, and standardized schemas.

                ## 📦 Tech Stack
                - Java 21
                - Spring Boot 3
                - Spring Data JPA
                - Hibernate
                - PostgreSQL
                - OpenAPI 3
                - JUnit 5 & Mockito

                🚀 Feel free to explore the endpoints below!
                """)
                .version("1.0.0")
                .contact(new Contact().name("Aldo Espinosa").email("aldoespinosaperez1@gmail.com")))
        .externalDocs(
            new ExternalDocumentation()
                .description("GitHub Repository")
                .url("https://github.com/iApolloBear/SpringBootEcommerceChallenge"))
        .tags(
            List.of(
                new Tag().name("Products").description("Operations related to products"),
                new Tag().name("Orders").description("Operations related to orders"),
                new Tag().name("Order Items").description("Operations related to order items")));
  }
}
