package com.aldo.ecommerce_challenge.products.services;

import com.aldo.ecommerce_challenge.products.ProductsData;
import com.aldo.ecommerce_challenge.products.dto.ProductCreateDTO;
import com.aldo.ecommerce_challenge.products.dto.ProductUpdateDTO;
import com.aldo.ecommerce_challenge.products.mappers.ProductMapper;
import com.aldo.ecommerce_challenge.products.models.Product;
import com.aldo.ecommerce_challenge.products.repositories.ProductRepository;
import org.junit.jupiter.api.DisplayName;
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
@DisplayName("ProductService Tests")
class ProductServiceImplTest {
  @MockitoBean ProductRepository productRepository;
  @MockitoBean ProductMapper productMapper;
  @Autowired ProductService productService;

  @Test
  @DisplayName("Should find all products")
  void testFindAll() {
    List<Product> mockProducts =
        List.of(
            ProductsData.createProductOne().orElseThrow(),
            ProductsData.createProductTwo().orElseThrow());
    when(this.productService.findAll()).thenReturn(mockProducts);
    List<Product> products = this.productService.findAll();

    assertFalse(products.isEmpty());
    assertEquals(2, products.size());
    assertTrue(products.contains(ProductsData.createProductTwo().orElseThrow()));

    verify(this.productRepository).findAll();
  }

  @Test
  @DisplayName("Should find product by ID")
  void testFindById() {
    when(this.productService.findById(1L)).thenReturn(ProductsData.createProductOne());
    Optional<Product> product = this.productService.findById(1L);
    assertTrue(product.isPresent());
    assertEquals("GUTS", product.get().getName());
    assertEquals("Olivia Rodrigo Album", product.get().getDescription());
    assertEquals("938", product.get().getPrice().toPlainString());
    assertFalse(this.productService.findById(2L).isPresent());
    verify(this.productRepository).findById(1L);
  }

  @Test
  @DisplayName("Should save a new product")
  void testSave() {
    Product product = new Product("RUSH!", "Maneskin Album", new BigDecimal("585.58"));
    ProductCreateDTO dto =
        new ProductCreateDTO(product.getName(), product.getDescription(), product.getPrice());
    when(this.productMapper.toProduct(dto)).thenReturn(product);
    when(this.productRepository.save(product))
        .then(
            invocation -> {
              Product p = invocation.getArgument(0);
              p.setId(3L);
              return p;
            });
    Product savedProduct = this.productService.save(dto);
    assertEquals("RUSH!", savedProduct.getName());
    assertEquals("Maneskin Album", savedProduct.getDescription());
    assertEquals("585.58", savedProduct.getPrice().toPlainString());

    verify(this.productRepository).save(any());
  }

  @Test
  @DisplayName("Should update an existing product")
  void testUpdate() {
    when(this.productRepository.findById(2L)).thenReturn(ProductsData.createProductTwo());
    Optional<Product> product = this.productService.findById(2L);
    assertTrue(product.isPresent());

    ProductUpdateDTO dto =
        new ProductUpdateDTO("Stoney", "Post Malone Album", new BigDecimal("877.68"));
    when(this.productRepository.save(any(Product.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    Optional<Product> result = this.productService.update(2L, dto);
    assertTrue(result.isPresent());
    assertEquals("Stoney", result.get().getName());
    assertEquals("Post Malone Album", result.get().getDescription());
    assertEquals("877.68", result.get().getPrice().toPlainString());

    verify(this.productRepository, times(2)).findById(2L);
    verify(this.productRepository).save(product.get());
  }

  @Test
  @DisplayName("Should delete a product")
  void testDelete() {
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
