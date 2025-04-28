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
                    "Simple Spring Boot API for Products, Orders, and Order Items management.")
                .version("v1.0.0")
                .contact(
                    new Contact()
                        .name("Aldo Espinosa")
                        .url("https://github.com/iApolloBear/SpringBootEcommerceChallenge")
                        .email("aldoespinosaperez1@gmail.com")))
        .externalDocs(
            new ExternalDocumentation()
                .description("SpringBootEcommerceChallenge")
                .url("https://github.com/iApolloBear/SpringBootEcommerceChallenge"))
        .tags(
            List.of(
                new Tag().name("Products").description("Operations related to products"),
                new Tag().name("Orders").description("Operations related to orders"),
                new Tag().name("Order Items").description("Operations related to order items")));
  }
}
