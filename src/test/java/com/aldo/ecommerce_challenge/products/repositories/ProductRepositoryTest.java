package com.aldo.ecommerce_challenge.products.repositories;

import com.aldo.ecommerce_challenge.orderItems.repositories.OrderItemRepository;
import com.aldo.ecommerce_challenge.products.models.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductRepositoryTest {
  @Autowired ProductRepository productRepository;
  @Autowired OrderItemRepository orderItemRepository;

  @Test
  @DisplayName("Should find all products")
  void testFindAll() {
    List<Product> products = (List<Product>) this.productRepository.findAll();
    assertFalse(products.isEmpty());
    assertEquals(2, products.size());
  }

  @Test
  @DisplayName("Should find product by id")
  void testFindById() {
    Optional<Product> product = this.productRepository.findById(1L);
    assertTrue(product.isPresent());
    assertEquals("GUTS", product.get().getName());
    assertEquals("Olivia Rodrigo Album", product.get().getDescription());
    assertEquals("938.00", product.get().getPrice().toPlainString());
    product = this.productRepository.findById(3L);
    assertFalse(product.isPresent());
  }

  @Test
  @DisplayName("Should save a new product successfully")
  void testSave() {
    Product abbeyRoad = new Product("Abbey Road", "The Beatles Album", new BigDecimal("779"));
    Product product = this.productRepository.save(abbeyRoad);
    assertEquals(3, product.getId());
    assertEquals(abbeyRoad.getName(), product.getName());
    assertEquals(abbeyRoad.getDescription(), product.getDescription());
    assertEquals(abbeyRoad.getPrice(), product.getPrice());
  }

  @Test
  @DisplayName("Should save a new product successfully")
  void testUpdate() {
    Product dummy = new Product("Dummy", "Portishead Album", new BigDecimal("779.33"));

    Product product = this.productRepository.save(dummy);

    assertEquals("Dummy", product.getName());
    assertEquals("Portishead Album", product.getDescription());
    assertEquals("779.33", product.getPrice().toPlainString());

    product.setName("Third");
    product.setPrice(new BigDecimal("1180"));
    Product updatedProduct = this.productRepository.save(product);

    assertEquals("Third", updatedProduct.getName());
    assertEquals("Portishead Album", product.getDescription());
    assertEquals("1180", updatedProduct.getPrice().toPlainString());
  }

  @Test
  @DisplayName("Should delete a product")
  void testDelete() {
    orderItemRepository
        .findAll()
        .forEach(
            item -> {
              if (item.getProduct().getId().equals(2L)) {
                orderItemRepository.delete(item);
              }
            });
    Product product = this.productRepository.findById(2L).orElseThrow();
    assertEquals("F-1 Trillion", product.getName());

    this.productRepository.delete(product);

    assertEquals(1, this.productRepository.count());
    assertThrows(NoSuchElementException.class, () -> productRepository.findById(2L).orElseThrow());
  }
}
