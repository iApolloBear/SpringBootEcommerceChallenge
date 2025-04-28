package com.aldo.ecommerce_challenge.products.controllers;

import com.aldo.ecommerce_challenge.products.dto.ProductCreateDTO;
import com.aldo.ecommerce_challenge.products.dto.ProductUpdateDTO;
import com.aldo.ecommerce_challenge.products.exceptions.ProductNotFoundException;
import com.aldo.ecommerce_challenge.products.models.Product;
import com.aldo.ecommerce_challenge.products.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Operations related to products")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @Operation(
      summary = "Find all products",
      description = "Get all products in the database.",
      responses = {@ApiResponse(responseCode = "200", description = "List of products")})
  @GetMapping
  public ResponseEntity<List<Product>> getAll() {
    return ResponseEntity.ok(this.productService.findAll());
  }

  @Operation(
      summary = "Find a product by ID",
      description = "Retrieve a product by its ID.",
      parameters = {@Parameter(name = "id", description = "Product ID", required = true)},
      responses = {
        @ApiResponse(responseCode = "200", description = "Product found"),
      })
  @GetMapping("/{id}")
  public ResponseEntity<Product> getById(@PathVariable Long id) {
    return this.productService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ProductNotFoundException(id));
  }

  @Operation(
      summary = "Create a new product",
      description = "Add a new product to the database.",
      responses = {
        @ApiResponse(responseCode = "201", description = "Product created"),
      })
  @PostMapping
  public ResponseEntity<Product> create(@Valid @RequestBody ProductCreateDTO dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.save(dto));
  }

  @Operation(
      summary = "Update an existing product",
      description = "Update fields of an existing product by ID.",
      parameters = {@Parameter(name = "id", description = "Product ID", required = true)},
      responses = {
        @ApiResponse(responseCode = "200", description = "Product updated"),
      })
  @PutMapping("/{id}")
  public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductUpdateDTO dto) {
    return this.productService
        .update(id, dto)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ProductNotFoundException(id));
  }

  @Operation(
      summary = "Delete a product",
      description = "Delete a product by ID.",
      parameters = {@Parameter(name = "id", description = "Product ID", required = true)},
      responses = {
        @ApiResponse(responseCode = "200", description = "Product deleted"),
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Product> delete(@PathVariable Long id) {
    return this.productService
        .delete(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ProductNotFoundException(id));
  }
}
