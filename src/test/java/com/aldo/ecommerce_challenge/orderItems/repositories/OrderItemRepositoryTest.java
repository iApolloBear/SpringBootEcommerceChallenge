package com.aldo.ecommerce_challenge.orderItems.repositories;

import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.orders.repositories.OrderRepository;
import com.aldo.ecommerce_challenge.products.models.Product;
import com.aldo.ecommerce_challenge.products.repositories.ProductRepository;
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
@DisplayName("OrderItemRepository Tests")
class OrderItemRepositoryTest {
  @Autowired private OrderItemRepository orderItemRepository;

  @Autowired private OrderRepository orderRepository;

  @Autowired private ProductRepository productRepository;

  @Test
  @DisplayName("Find all OrderItems")
  void testFindAll() {
    List<OrderItem> orderItems = (List<OrderItem>) this.orderItemRepository.findAll();
    assertEquals(3, orderItems.size());
  }

  @Test
  @DisplayName("Find an OrderItem by ID")
  void testFindById() {
    Optional<OrderItem> orderItem = this.orderItemRepository.findById(1L);
    assertTrue(orderItem.isPresent());
    assertEquals(1, orderItem.get().getId());
    assertEquals(1, orderItem.get().getOrder().getId());
    assertEquals(1, orderItem.get().getProduct().getId());
    assertEquals("938.00", orderItem.get().getPrice().toPlainString());
    orderItem = this.orderItemRepository.findById(4L);
    assertFalse(orderItem.isPresent());
  }

  @Test
  @DisplayName("Save a new OrderItem")
  void testSave() {
    Order order = this.orderRepository.findById(1L).orElseThrow();
    Product product = this.productRepository.findById(2L).orElseThrow();

    OrderItem orderItem = new OrderItem(order, product, 2);
    OrderItem saved = orderItemRepository.save(orderItem);

    assertNotNull(saved);
  }

  @Test
  @DisplayName("Update an OrderItem")
  void testUpdate() {
    OrderItem orderItem = this.orderItemRepository.findById(1L).orElseThrow();
    orderItem.setQuantity(5);
    orderItem.setPrice(
        orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));

    OrderItem updated = this.orderItemRepository.save(orderItem);

    assertEquals("4690.00", updated.getPrice().toPlainString());
    assertEquals(5, updated.getQuantity());
    assertEquals(1, updated.getId());
    assertEquals(1, updated.getProduct().getId());
    assertEquals(1, updated.getOrder().getId());
  }

  @Test
  @DisplayName("Delete an OrderItem")
  void testDelete() {
    OrderItem orderItem = this.orderItemRepository.findById(3L).orElseThrow();
    assertEquals(3, orderItem.getId());

    this.orderItemRepository.delete(orderItem);

    assertThrows(
        NoSuchElementException.class, () -> this.orderItemRepository.findById(3L).orElseThrow());
    assertEquals(2, this.orderItemRepository.count());
  }

  @Test
  @DisplayName("Find OrderItems by a list of IDs")
  void testFindByIdIn() {
    List<OrderItem> items = orderItemRepository.findByIdIn(List.of(1L, 2L, 4L));

    assertEquals(2, items.size());
  }
}
