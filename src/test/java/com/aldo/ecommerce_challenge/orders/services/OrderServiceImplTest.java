package com.aldo.ecommerce_challenge.orders.services;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orderItems.repositories.OrderItemRepository;
import com.aldo.ecommerce_challenge.orders.OrdersData;
import com.aldo.ecommerce_challenge.orders.dto.OrderDTO;
import com.aldo.ecommerce_challenge.orders.dto.OrderUpdateDTO;
import com.aldo.ecommerce_challenge.orders.mappers.OrderMapper;
import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.orders.repositories.OrderRepository;
import com.aldo.ecommerce_challenge.products.ProductsData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("OrderService Tests")
public class OrderServiceImplTest {
  @MockitoBean OrderRepository orderRepository;
  @MockitoBean OrderMapper orderMapper;
  @MockitoBean OrderItemRepository orderItemRepository;
  @Autowired OrderService orderService;

  @Test
  @DisplayName("Should return all orders")
  void testFindAll() {
    Order order1 = OrdersData.createOrderOne().orElseThrow();
    Order order2 = OrdersData.createOrderTwo().orElseThrow();
    OrderDTO orderDTO = OrdersData.createOrderDTOOne().orElseThrow();
    when(orderRepository.findAll()).thenReturn(List.of(order1, order2));
    when(orderMapper.toDto(order1)).thenReturn(orderDTO);

    List<OrderDTO> orders = orderService.findAll();

    assertFalse(orders.isEmpty());
    assertEquals(2, orders.size());
    assertTrue(orders.contains(orderDTO));
    verify(this.orderRepository).findAll();
  }

  @Test
  @DisplayName("Find order by id successfully")
  void testFindById() {
    Optional<Order> order1 = OrdersData.createOrderOne();
    Optional<Order> order2 = OrdersData.createOrderTwo();
    when(orderRepository.findById(1L)).thenReturn(order1);
    when(orderRepository.findById(2L)).thenReturn(order2);
    when(orderMapper.toDto(order1.orElseThrow()))
        .thenReturn(OrdersData.createOrderDTOOne().orElseThrow());
    when(orderMapper.toDto(order2.orElseThrow()))
        .thenReturn(OrdersData.createOrderDTOTwo().orElseThrow());

    Optional<OrderDTO> result1 = orderService.findById(1L);
    Optional<OrderDTO> result2 = orderService.findById(2L);

    assertTrue(result1.isPresent());
    assertEquals(1L, result1.get().getId());
    assertTrue(result2.isPresent());
    assertEquals(2L, result2.get().getId());

    verify(this.orderRepository).findById(1L);
    verify(this.orderRepository).findById(2L);
  }

  @Test
  @DisplayName("Save new order successfully")
  void testSave() {
    Order order = new Order();
    OrderDTO dto = new OrderDTO(3L, order.getTotal(), List.of());
    when(orderRepository.save(any())).thenReturn(order);
    when(orderMapper.toDto(order)).thenReturn(dto);

    OrderDTO result = orderService.save();

    assertEquals(3L, result.getId());
    assertTrue(result.getOrderItems().isEmpty());
    assertEquals("0", result.getTotal().toPlainString());
    verify(this.orderRepository).save(order);
  }

  @Test
  @DisplayName("Update order successfully")
  void testUpdate() {
    when(this.orderRepository.findById(1L)).thenReturn(Optional.of(new Order()));
    Optional<Order> order = this.orderRepository.findById(1L);
    assertTrue(order.isPresent());
    OrderItem orderItem1 =
        new OrderItem(1L, order.get(), ProductsData.createProductOne().orElseThrow(), 1);
    OrderItem orderItem2 =
        new OrderItem(2L, order.get(), ProductsData.createProductTwo().orElseThrow(), 1);
    order.get().addItem(orderItem1);
    order.get().addItem(orderItem2);
    OrderUpdateDTO orderUpdateDTO =
        new OrderUpdateDTO(List.of(orderItem1.getId(), orderItem2.getId()));
    when(this.orderRepository.save(any())).thenReturn(order.get());
    when(this.orderItemRepository.findByIdIn(List.of(1L, 2L)))
        .thenReturn(List.of(orderItem1, orderItem2));
    when(this.orderMapper.toDto(order.get()))
        .thenReturn(OrdersData.createOrderDTOOne().orElseThrow());
    Optional<OrderDTO> result = orderService.update(1L, orderUpdateDTO);

    assertTrue(result.isPresent());
    assertEquals(1, result.get().getId());
    assertEquals(2, result.get().getOrderItems().size());
    assertEquals("2437", result.get().getTotal().toPlainString());
    verify(this.orderRepository).save(any());
    verify(this.orderItemRepository).findByIdIn(List.of(1L, 2L));
  }

  @Test
  @DisplayName("Delete order successfully")
  void delete_ShouldDeleteOrder() {
    Optional<Order> order = OrdersData.createOrderOne();
    Optional<OrderDTO> orderDTO = OrdersData.createOrderDTOOne();
    when(orderRepository.findById(1L)).thenReturn(order);
    when(orderMapper.toDto(order.orElseThrow())).thenReturn(orderDTO.orElseThrow());

    Optional<OrderDTO> result = orderService.delete(1L);

    assertTrue(result.isPresent());
    assertEquals(1L, result.get().getId());

    verify(this.orderRepository).findById(1L);
    verify(this.orderRepository).delete(order.get());
  }
}
