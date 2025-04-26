package com.aldo.ecommerce_challenge.orders.services;

import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orders.OrdersData;
import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.orders.repositories.OrderRepository;
import com.aldo.ecommerce_challenge.products.ProductsData;
import com.aldo.ecommerce_challenge.products.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceImplementationTest {
  @MockitoBean OrderRepository orderRepository;
  @Autowired OrderService orderService;

  @Test
  void testFindAll() {
    List<Order> data =
        List.of(
            OrdersData.createOrderOne().orElseThrow(), OrdersData.createOrderTwo().orElseThrow());
    when(this.orderService.findAll()).thenReturn(data);
    List<Order> orders = this.orderService.findAll();
    assertFalse(orders.isEmpty());
    assertEquals(2, orders.size());
    verify(this.orderRepository).findAll();
  }

  @Test
  void testFindById() {
    when(this.orderService.findById(1L)).thenReturn(OrdersData.createOrderOne());
    when(this.orderService.findById(2L)).thenReturn(OrdersData.createOrderTwo());
    Optional<Order> order1 = this.orderService.findById(1L);
    Optional<Order> order2 = this.orderService.findById(2L);
    assertTrue(order1.isPresent());
    assertEquals(1, order1.get().getId());
    assertEquals("6373", order1.get().getTotal().toPlainString());
    assertTrue(order2.isPresent());
    assertEquals(2, order2.get().getId());
    assertEquals("0", order2.get().getTotal().toPlainString());
    assertFalse(this.orderService.findById(3L).isPresent());
    verify(this.orderRepository, times(3)).findById(anyLong());
  }

  @Test
  void testSave() {
    Order newOrder = new Order();
    OrderItem orderItem = new OrderItem(newOrder, ProductsData.createProductOne().orElseThrow(), 1);
    newOrder.addItem(orderItem);
    when(this.orderRepository.save(any()))
        .then(
            invocation -> {
              Order o = invocation.getArgument(0);
              o.setId(3L);
              return o;
            });
    Order order = this.orderService.save(newOrder);
    assertEquals(3, order.getId());
    assertEquals(1, order.getOrderItems().size());
    assertEquals("938", order.getTotal().toPlainString());
    verify(this.orderRepository).save(any());
  }

  @Test
  void testUpdate() {
    when(this.orderRepository.findById(1L)).thenReturn(OrdersData.createOrderOne());
    Optional<Order> order = this.orderService.findById(1L);
    assertTrue(order.isPresent());
    Order newOrder = new Order();
    OrderItem orderItem = new OrderItem(newOrder, ProductsData.createProductTwo().orElseThrow(), 2);
    newOrder.addItem(orderItem);
    when(this.orderRepository.save(any(Order.class)))
        .then(
            invocation -> {
              Order o = invocation.getArgument(0);
              o.setId(1L);
              return o;
            });
    Optional<Order> result = this.orderService.update(1L, newOrder);
    assertTrue(result.isPresent());
    assertEquals(1, result.get().getId());
    assertEquals("2998", result.get().getTotal().toPlainString());
    assertEquals(1, result.get().getOrderItems().size());
    verify(this.orderRepository).save(any());
  }

  @Test
  void testDelete() {
    when(this.orderRepository.findById(2L)).thenReturn(OrdersData.createOrderTwo());
    Optional<Order> order = this.orderService.findById(2L);
    assertTrue(order.isPresent());
    Optional<Order> result = this.orderService.delete(2L);
    assertTrue(result.isPresent());
    assertEquals(2, result.get().getId());
    assertEquals("0", result.get().getTotal().toPlainString());

    verify(this.orderRepository, times(2)).findById(2L);
    verify(this.orderRepository).delete(order.get());
  }
}
