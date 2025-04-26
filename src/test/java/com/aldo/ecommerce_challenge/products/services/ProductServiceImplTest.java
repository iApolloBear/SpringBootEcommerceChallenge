package com.aldo.ecommerce_challenge.products.services;

import com.aldo.ecommerce_challenge.products.ProductsData;
import com.aldo.ecommerce_challenge.products.models.Product;
import com.aldo.ecommerce_challenge.products.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {
  @MockitoBean ProductRepository productRepository;
  @Autowired ProductService productService;

  @Test
  void findAll() {
    List<Product> data =
        List.of(
            ProductsData.createProductOne().orElseThrow(),
            ProductsData.createProductTwo().orElseThrow());
    when(this.productService.findAll()).thenReturn(data);
    List<Product> products = this.productService.findAll();

    assertFalse(products.isEmpty());
    assertEquals(2, products.size());
    assertTrue(products.contains(ProductsData.createProductTwo().orElseThrow()));

    verify(this.productRepository).findAll();
  }

  @Test
  void findById() {
    when(this.productService.findById(1L)).thenReturn(ProductsData.createProductOne());
    Optional<Product> product = this.productService.findById(1L);
    assertTrue(product.isPresent());
    assertEquals("GUTS", product.get().getName());
    assertEquals("Olivia Rodrigo Album", product.get().getDescription());
    assertEquals("938", product.get().getPrice().toPlainString());
    assertFalse(this.productService.findById(2L).isPresent());
  }

  @Test
  void save() {
    Product rush = new Product("RUSH!", "Maneskin Album", new BigDecimal("585.58"));
    when(this.productRepository.save(any()))
        .then(
            invocation -> {
              Product p = invocation.getArgument(0);
              p.setId(3L);
              return p;
            });
    Product product = this.productService.save(rush);
    assertEquals("RUSH!", product.getName());
    assertEquals("Maneskin Album", product.getDescription());
    assertEquals("585.58", product.getPrice().toPlainString());

    verify(this.productRepository).save(any());
  }

  @Test
  void update() {
    when(this.productRepository.findById(2L)).thenReturn(ProductsData.createProductTwo());
    Optional<Product> product = this.productService.findById(2L);
    assertTrue(product.isPresent());
    Product newProduct = new Product(2L, "Stoney", "Post Malone Album", new BigDecimal("877.68"));
    when(this.productRepository.save(any(Product.class))).thenReturn(newProduct);
    Optional<Product> result = this.productService.update(2L, newProduct);
    assertTrue(result.isPresent());
    assertEquals("Stoney", result.get().getName());
    assertEquals("Post Malone Album", result.get().getDescription());
    assertEquals("877.68", result.get().getPrice().toPlainString());

    verify(this.productRepository, times(2)).findById(2L);
    verify(this.productRepository).save(any());
  }

  @Test
  void delete() {
    when(this.productRepository.findById(1L)).thenReturn(ProductsData.createProductOne());
    Optional<Product> product = this.productService.findById(1L);
    assertTrue(product.isPresent());
    Optional<Product> result = this.productService.delete(1L);
    assertTrue(result.isPresent());
    assertEquals("GUTS", result.get().getName());

    verify(this.productRepository, times(2)).findById(1L);
    verify(this.productRepository).delete(product.get());
  }
}
