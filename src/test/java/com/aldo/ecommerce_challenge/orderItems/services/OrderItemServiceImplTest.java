package com.aldo.ecommerce_challenge.orderItems.services;

import com.aldo.ecommerce_challenge.orderItems.OrderItemsData;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemUpdateDTO;
import com.aldo.ecommerce_challenge.orderItems.mappers.OrderItemMapper;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orderItems.repositories.OrderItemRepository;
import com.aldo.ecommerce_challenge.orders.OrdersData;
import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.orders.repositories.OrderRepository;
import com.aldo.ecommerce_challenge.products.ProductsData;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("OrderItemsService Tests")
class OrderItemServiceImplTest {
  @MockitoBean private OrderItemRepository orderItemRepository;
  @MockitoBean private ProductRepository productRepository;
  @MockitoBean private OrderRepository orderRepository;
  @MockitoBean private OrderItemMapper orderItemMapper;
  @Autowired private OrderItemServiceImpl orderItemService;

  @Test
  @DisplayName("Should return all order items")
  void testFindAll() {
    List<OrderItem> orderItems =
        List.of(new OrderItem(), new OrderItem(), new OrderItem(), new OrderItem());
    when(orderItemRepository.findAll()).thenReturn(orderItems);

    List<OrderItemDTO> result = orderItemService.findAll();

    assertEquals(4, result.size());
    verify(orderItemRepository).findAll();
  }

  @Test
  @DisplayName("Should find order item by id")
  void testFindById() {
    OrderItem orderItem = new OrderItem();
    OrderItemDTO dto = OrderItemsData.createOrderItemDTOOne();

    when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
    when(orderItemMapper.toOrderItemDto(orderItem)).thenReturn(dto);

    Optional<OrderItemDTO> result = orderItemService.findById(1L);

    assertTrue(result.isPresent());
    assertEquals(dto.getId(), result.get().getId());
    assertEquals(dto.getProductId(), result.get().getProductId());
    assertEquals(dto.getPrice().toPlainString(), result.get().getPrice().toPlainString());
    assertEquals(dto.getQuantity(), result.get().getQuantity());
    verify(orderItemRepository).findById(1L);
  }

  @Test
  @DisplayName("Should save order item")
  void testSave() {
    OrderItemCreateDTO dto = OrderItemsData.createOrderItemCreateDTO();
    Product product = ProductsData.createProductTwo().orElseThrow();
    Order order = new Order();
    order.setId(1L);
    OrderItem orderItem = new OrderItem(order, product, 2);
    OrderItemDTO orderItemDTO =
        new OrderItemDTO(
            orderItem.getId(),
            orderItem.getOrder().getId(),
            orderItem.getProduct().getId(),
            orderItem.getQuantity(),
            orderItem.getPrice());

    when(productRepository.findById(2L)).thenReturn(Optional.of(product));
    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
    when(orderItemRepository.save(orderItem)).thenReturn(orderItem);
    when(orderItemMapper.toOrderItemDto(orderItem)).thenReturn(orderItemDTO);

    OrderItemDTO result = orderItemService.save(dto);

    assertNotNull(result);
    assertEquals(orderItem.getQuantity(), result.getQuantity());
    assertEquals(orderItem.getOrder().getId(), result.getOrderId());
    assertEquals(orderItem.getProduct().getId(), result.getProductId());
    assertEquals(orderItem.getQuantity(), result.getQuantity());
    assertEquals(orderItem.getPrice(), result.getPrice());
    verify(orderItemRepository).save(orderItem);
  }

  @Test
  @DisplayName("Should update order item quantity")
  void testUpdateQuantity() {
    Product product = ProductsData.createProductOne().orElseThrow();
    Order order = OrdersData.createOrderOne().orElseThrow();
    OrderItem orderItem = new OrderItem(order, product, 1);

    OrderItemUpdateDTO dto = new OrderItemUpdateDTO(order.getId(), product.getId(), 3);
    BigDecimal newPrice = product.getPrice().multiply(BigDecimal.valueOf(3));
    OrderItemDTO expectedDto =
        new OrderItemDTO(orderItem.getId(), order.getId(), product.getId(), 3, newPrice);

    when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    when(orderItemRepository.save(any())).thenReturn(orderItem);
    when(orderItemMapper.toOrderItemDto(orderItem)).thenReturn(expectedDto);

    Optional<OrderItemDTO> result = orderItemService.update(1L, dto);

    assertTrue(result.isPresent());
    assertEquals(newPrice, result.get().getPrice());
    assertEquals(3, result.get().getQuantity());
    assertEquals(new BigDecimal("7127"), order.getTotal().add(newPrice));
    verify(this.orderItemRepository).save(orderItem);
  }

  @Test
  @DisplayName("Should update orderId of order item")
  void testUpdateOrderId() {
    Order oldOrder = OrdersData.createOrderOne().orElseThrow();
    Order newOrder = OrdersData.createOrderTwo().orElseThrow();
    Product product = ProductsData.createProductOne().orElseThrow();
    OrderItem orderItem = new OrderItem(1L, oldOrder, product, 1);

    OrderItemUpdateDTO dto = new OrderItemUpdateDTO(2L, null, null);
    OrderItemDTO expectedDto =
        new OrderItemDTO(
            orderItem.getId(),
            2L,
            orderItem.getProduct().getId(),
            orderItem.getQuantity(),
            orderItem.getPrice());

    when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
    when(orderRepository.findById(2L)).thenReturn(Optional.of(newOrder));
    when(orderItemRepository.save(orderItem)).thenReturn(orderItem);
    when(orderItemMapper.toOrderItemDto(orderItem)).thenReturn(expectedDto);

    Optional<OrderItemDTO> result = orderItemService.update(1L, dto);

    assertTrue(result.isPresent());
    assertEquals(expectedDto, result.get());
    assertEquals(newOrder.getId(), result.get().getOrderId());
    verify(this.orderItemRepository).save(orderItem);
  }

  @Test
  @DisplayName("Should update productId of order item")
  void testUpdateProductId() {
    Product oldProduct = ProductsData.createProductOne().orElseThrow();
    Product newProduct = ProductsData.createProductTwo().orElseThrow();
    Order order = OrdersData.createOrderOne().orElseThrow();

    OrderItem orderItem = new OrderItem(1L, order, oldProduct, 1);

    OrderItemUpdateDTO dto = new OrderItemUpdateDTO(null, 2L, null);
    OrderItemDTO expectedDto =
        new OrderItemDTO(
            orderItem.getId(),
            orderItem.getOrder().getId(),
            2L,
            orderItem.getQuantity(),
            orderItem.getPrice());

    when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
    when(productRepository.findById(2L)).thenReturn(Optional.of(newProduct));
    when(orderItemRepository.save(orderItem)).thenReturn(orderItem);
    when(orderItemMapper.toOrderItemDto(orderItem)).thenReturn(expectedDto);

    Optional<OrderItemDTO> result = orderItemService.update(1L, dto);

    assertTrue(result.isPresent());
    assertEquals(expectedDto, result.get());
    assertEquals(newProduct.getId(), result.get().getProductId());
    verify(this.orderItemRepository).save(orderItem);
  }

  @Test
  @DisplayName("Should delete order item when quantity is zero")
  void testDeleteWhenQuantityZero() {
    Product product = ProductsData.createProductOne().orElseThrow();
    Order order = new Order();

    OrderItem orderItem = new OrderItem(1L, order, product, 1);

    OrderItemUpdateDTO dto = new OrderItemUpdateDTO(null, null, 0);
    OrderItemDTO expectedDto =
        new OrderItemDTO(orderItem.getId(), order.getId(), product.getId(), 0, product.getPrice());

    when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
    when(orderItemMapper.toOrderItemDto(orderItem)).thenReturn(expectedDto);

    Optional<OrderItemDTO> result = orderItemService.update(1L, dto);

    assertTrue(result.isPresent());
    assertEquals(0, result.get().getQuantity());
    verify(orderItemRepository).delete(orderItem);
    assertEquals(expectedDto, result.get());
  }

  @Test
  @DisplayName("Should delete order item")
  void testDelete() {
    Order order = new Order();
    order.setId(1L);
    OrderItem orderItem = new OrderItem();
    orderItem.setOrder(order);
    OrderItemDTO dto =
        new OrderItemDTO(
            1L,
            order.getId(),
            ProductsData.createProductOne().orElseThrow().getId(),
            orderItem.getQuantity(),
            orderItem.getPrice());

    when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
    when(orderItemMapper.toOrderItemDto(orderItem)).thenReturn(dto);

    Optional<OrderItemDTO> result = orderItemService.delete(1L);

    assertTrue(result.isPresent());
    verify(orderItemRepository).delete(orderItem);
  }
}
