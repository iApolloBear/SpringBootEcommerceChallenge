package com.aldo.ecommerce_challenge.products.controllers;

import com.aldo.ecommerce_challenge.products.models.Product;
import com.aldo.ecommerce_challenge.products.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<List<Product>> getAll() {
    return ResponseEntity.ok(this.productService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getById(@PathVariable Long id) {
    return this.productService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Product> create(@RequestBody Product product) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.save(product));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
    return this.productService
        .update(id, product)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Product> delete(@PathVariable Long id) {
    return this.productService
        .delete(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
