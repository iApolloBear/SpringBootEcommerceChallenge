package com.aldo.ecommerce_challenge.orders.repositories;

import com.aldo.ecommerce_challenge.orders.models.Order;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("OrderRepository Tests")
public class OrderRepositoryTest {
  @Autowired OrderRepository orderRepository;

  @Test
  @DisplayName("Should find all products")
  public void testFindAll() {
    List<Order> orders = (List<Order>) this.orderRepository.findAll();
    assertFalse(orders.isEmpty());
    assertEquals(2, orders.size());
  }

  @Test
  @DisplayName("Should find a product by ID")
  public void testFindById() {
    Optional<Order> order = this.orderRepository.findById(1L);
    assertTrue(order.isPresent());
    assertEquals("938.00", order.get().getTotal().toPlainString());
    order = this.orderRepository.findById(3L);
    assertFalse(order.isPresent());
  }

  @Test
  @DisplayName("Should save a new product")
  public void testSave() {
    Order order = new Order();
    Order orderDb = this.orderRepository.save(order);
    assertNotNull(orderDb);
    assertTrue(orderDb.getOrderItems().isEmpty());
    assertEquals("0", orderDb.getTotal().toPlainString());
    assertEquals(0, orderDb.getOrderItems().size());
  }

  @Test
  @DisplayName("Should update an existing product")
  public void testUpdate() {
    Order newOrder = new Order();
    newOrder.setTotal(new BigDecimal("724.69"));
    Order order = this.orderRepository.save(newOrder);
    assertEquals("724.69", order.getTotal().toPlainString());
    order.setTotal(new BigDecimal("938.00"));
    Order updatedProduct = this.orderRepository.save(order);
    assertEquals("938.00", updatedProduct.getTotal().toPlainString());
  }

  @Test
  @DisplayName("Should delete an order")
  public void testDelete() {
    Order order = this.orderRepository.findById(1L).orElseThrow();
    assertEquals("938.00", order.getTotal().toPlainString());

    this.orderRepository.delete(order);

    assertThrows(
        NoSuchElementException.class, () -> this.orderRepository.findById(1L).orElseThrow());

    assertEquals(1, List.of(this.orderRepository.findAll()).size());
  }
}
