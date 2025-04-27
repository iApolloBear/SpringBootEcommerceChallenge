package com.aldo.ecommerce_challenge.orderItems.services;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateUpdateDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.mappers.OrderItemMapper;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orderItems.repositories.OrderItemRepository;
import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.orders.repositories.OrderRepository;
import com.aldo.ecommerce_challenge.products.models.Product;
import com.aldo.ecommerce_challenge.products.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderItemServiceImpl implements OrderItemService {
  private final OrderItemRepository orderItemRepository;
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final OrderItemMapper orderItemMapper;

  public OrderItemServiceImpl(
      OrderItemRepository orderItemRepository,
      ProductRepository productRepository,
      OrderRepository orderRepository,
      OrderItemMapper orderItemMapper) {
    this.orderItemRepository = orderItemRepository;
    this.productRepository = productRepository;
    this.orderRepository = orderRepository;
    this.orderItemMapper = orderItemMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public List<OrderItemDTO> findAll() {
    return StreamSupport.stream(this.orderItemRepository.findAll().spliterator(), false)
        .map(this.orderItemMapper::toOrderItemDto)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<OrderItemDTO> findById(Long id) {
    return this.orderItemRepository.findById(id).map(this.orderItemMapper::toOrderItemDto);
  }

  @Override
  @Transactional
  public OrderItemDTO save(OrderItemCreateUpdateDTO orderItemDto) {
    Product product = this.productRepository.findById(orderItemDto.getProductId()).orElseThrow();
    Order order = this.orderRepository.findById(orderItemDto.getOrderId()).orElseThrow();
    OrderItem orderItem = new OrderItem(order, product, orderItemDto.getQuantity());
    order.addItem(orderItem);
    this.orderRepository.save(order);
    return this.orderItemMapper.toOrderItemDto(this.orderItemRepository.save(orderItem));
  }

  @Override
  @Transactional
  public Optional<OrderItemDTO> update(Long id, OrderItemCreateUpdateDTO orderItemDto) {
    Product product = this.productRepository.findById(orderItemDto.getProductId()).orElseThrow();
    Order order = this.orderRepository.findById(orderItemDto.getOrderId()).orElseThrow();
    return this.orderItemRepository
        .findById(id)
        .map(
            orderItemDb -> {
              if (orderItemDto.getQuantity() <= 0) {
                this.orderItemRepository.delete(orderItemDb);
                OrderItemDTO result = this.orderItemMapper.toOrderItemDto(orderItemDb);
                order.removeItem(orderItemDb);
                this.orderRepository.save(order);
                return result;
              }

              orderItemDb.setOrder(order);
              orderItemDb.setProduct(product);
              orderItemDb.setQuantity(orderItemDto.getQuantity());
              orderItemDb.setPrice(
                  product.getPrice().multiply(BigDecimal.valueOf(orderItemDb.getQuantity())));

              OrderItem updatedOrderItem = this.orderItemRepository.save(orderItemDb);
              return this.orderItemMapper.toOrderItemDto(updatedOrderItem);
            });
  }

  @Override
  public Optional<OrderItemDTO> delete(Long id) {
    return this.orderItemRepository
        .findById(id)
        .map(
            orderItem -> {
              Order order =
                  this.orderRepository.findById(orderItem.getOrder().getId()).orElseThrow();
              order.removeItem(orderItem);
              this.orderRepository.save(order);
              this.orderItemRepository.delete(orderItem);
              return this.orderItemMapper.toOrderItemDto(orderItem);
            });
  }
}
