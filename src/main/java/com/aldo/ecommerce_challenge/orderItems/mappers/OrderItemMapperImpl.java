package com.aldo.ecommerce_challenge.orderItems.mappers;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTOWithProduct;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemUpdateDTO;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.orders.repositories.OrderRepository;
import com.aldo.ecommerce_challenge.products.dto.ProductUpdateDTO;
import com.aldo.ecommerce_challenge.products.models.Product;
import com.aldo.ecommerce_challenge.products.repositories.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderItemMapperImpl implements OrderItemMapper {
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;

  public OrderItemMapperImpl(OrderRepository orderRepository, ProductRepository productRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
  }

  @Override
  public OrderItem toOrderItem(OrderItemDTO dto) {
    Order order = this.orderRepository.findById(dto.getOrderId()).orElseThrow();
    Product product = this.productRepository.findById(dto.getProductId()).orElseThrow();
    return new OrderItem(dto.getId(), order, product, dto.getQuantity());
  }

  @Override
  public OrderItem toOrderItem(OrderItemDTOWithProduct dto) {
    Order order = this.orderRepository.findById(dto.getOrderId()).orElseThrow();
    return new OrderItem(dto.getId(), order, dto.getProduct(), dto.getQuantity());
  }

  @Override
  public OrderItem toOrderItem(OrderItemCreateDTO dto) {
    Order order = this.orderRepository.findById(dto.getOrderId()).orElseThrow();
    Product product = this.productRepository.findById(dto.getProductId()).orElseThrow();
    return new OrderItem(order, product, dto.getQuantity());
  }

  @Override
  public OrderItem toOrderItem(OrderItemUpdateDTO dto) {
    Order order = this.orderRepository.findById(dto.getOrderId()).orElseThrow();
    Product product = this.productRepository.findById(dto.getProductId()).orElseThrow();
    return new OrderItem(order, product, dto.getQuantity());
  }

  @Override
  public OrderItemDTO toOrderItemDto(OrderItem orderItem) {
    return new OrderItemDTO(
        orderItem.getId(),
        orderItem.getOrder().getId(),
        orderItem.getProduct().getId(),
        orderItem.getQuantity(),
        orderItem.getPrice());
  }

  @Override
  public OrderItemDTOWithProduct toOrderItemDtoWithProduct(OrderItem orderItem) {
    return new OrderItemDTOWithProduct(
        orderItem.getId(),
        orderItem.getOrder().getId(),
        orderItem.getProduct(),
        orderItem.getQuantity(),
        orderItem.getPrice());
  }

  @Override
  public OrderItemCreateDTO toOrderItemCreateDto(OrderItem orderItem) {
    return new OrderItemCreateDTO(
        orderItem.getOrder().getId(), orderItem.getProduct().getId(), orderItem.getQuantity());
  }

  @Override
  public OrderItemUpdateDTO toOrderItemUpdateDto(OrderItem orderItem) {
    return new OrderItemUpdateDTO(
        orderItem.getOrder().getId(), orderItem.getProduct().getId(), orderItem.getQuantity());
  }
}
