package com.aldo.ecommerce_challenge.orders.services;

import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orderItems.repositories.OrderItemRepository;
import com.aldo.ecommerce_challenge.orders.dto.OrderUpdateDTO;
import com.aldo.ecommerce_challenge.orders.dto.OrderDTO;
import com.aldo.ecommerce_challenge.orders.mappers.OrderMapper;
import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.orders.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final OrderItemRepository orderItemRepository;

  public OrderServiceImpl(
      OrderRepository orderRepository,
      OrderMapper orderMapper,
      OrderItemRepository orderItemRepository) {
    this.orderRepository = orderRepository;
    this.orderMapper = orderMapper;
    this.orderItemRepository = orderItemRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<OrderDTO> findAll() {
    return StreamSupport.stream(this.orderRepository.findAll().spliterator(), false)
        .map(this.orderMapper::toDto)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<OrderDTO> findById(Long id) {
    return this.orderRepository.findById(id).map(this.orderMapper::toDto);
  }

  @Override
  @Transactional
  public OrderDTO save() {
    return this.orderMapper.toDto(this.orderRepository.save(new Order()));
  }

  @Override
  @Transactional
  public Optional<OrderDTO> update(Long id, OrderUpdateDTO dto) {
    List<OrderItem> orderItems = this.orderItemRepository.findByIdIn(dto.getOrderItemIds());
    return this.orderRepository
        .findById(id)
        .map(
            order -> {
              orderItems.forEach(
                  orderItem -> {
                    orderItem.getOrder().removeItem(orderItem);
                    order.addItem(orderItem);
                  });
              this.orderRepository.save(order);
              return this.orderMapper.toDto(order);
            });
  }

  @Override
  @Transactional
  public Optional<OrderDTO> delete(Long id) {
    return orderRepository
        .findById(id)
        .map(
            order -> {
              this.orderRepository.delete(order);
              return this.orderMapper.toDto(order);
            });
  }
}
