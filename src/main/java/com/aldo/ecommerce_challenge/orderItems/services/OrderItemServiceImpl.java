package com.aldo.ecommerce_challenge.orderItems.services;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateUpdateDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orderItems.repositories.OrderItemRepository;
import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.orders.repositories.OrderRepository;
import com.aldo.ecommerce_challenge.products.models.Product;
import com.aldo.ecommerce_challenge.products.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class OrderItemServiceImpl implements OrderItemService {
  private final OrderItemRepository orderItemRepository;
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;

  public OrderItemServiceImpl(
      OrderItemRepository repository,
      ProductRepository productRepository,
      OrderRepository orderRepository) {
    this.orderItemRepository = repository;
    this.productRepository = productRepository;
    this.orderRepository = orderRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<OrderItemDTO> findAll() {
    return StreamSupport.stream(this.orderItemRepository.findAll().spliterator(), false)
        .map(
            item ->
                new OrderItemDTO(
                    item.getId(),
                    item.getOrder().getId(),
                    item.getProduct(),
                    item.getQuantity(),
                    item.getPrice()))
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<OrderItemDTO> findById(Long id) {
    Optional<OrderItem> orderItem = this.orderItemRepository.findById(id);
    return orderItem.map(
        item ->
            new OrderItemDTO(
                item.getId(),
                item.getOrder().getId(),
                item.getProduct(),
                item.getQuantity(),
                item.getPrice()));
  }

  @Override
  @Transactional
  public OrderItemDTO save(OrderItemCreateUpdateDTO orderItemDto) {
    Product product = this.productRepository.findById(orderItemDto.getProductId()).orElseThrow();
    Order order = this.orderRepository.findById(orderItemDto.getOrderId()).orElseThrow();
    OrderItem orderItem = new OrderItem(order, product, orderItemDto.getQuantity());
    return new OrderItemDTO(
        orderItem.getId(),
        orderItem.getOrder().getId(),
        orderItem.getProduct(),
        orderItem.getQuantity(),
        orderItem.getPrice());
  }

  @Override
  @Transactional
  public Optional<OrderItem> update(Long id, OrderItemCreateUpdateDTO orderItemDto) {
    Product product = this.productRepository.findById(orderItemDto.getProductId()).orElseThrow();
    Order order = this.orderRepository.findById(orderItemDto.getOrderId()).orElseThrow();
    return this.orderItemRepository
        .findById(id)
        .map(
            orderItemDb -> {
              orderItemDb.setOrder(order);
              orderItemDb.setProduct(product);
              orderItemDb.setQuantity(orderItemDto.getQuantity());
              return this.orderItemRepository.save(orderItemDb);
            });
  }

  @Override
  public Optional<OrderItem> delete(Long id) {
    return this.orderItemRepository
        .findById(id)
        .map(
            orderItem -> {
              this.orderItemRepository.delete(orderItem);
              return orderItem;
            });
  }
}
