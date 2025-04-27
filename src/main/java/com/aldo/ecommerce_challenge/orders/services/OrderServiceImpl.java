package com.aldo.ecommerce_challenge.orders.services;

import com.aldo.ecommerce_challenge.orderItems.mappers.OrderItemMapper;
import com.aldo.ecommerce_challenge.orders.dto.OrderCreateUpdateDTO;
import com.aldo.ecommerce_challenge.orders.dto.OrderDTO;
import com.aldo.ecommerce_challenge.orders.mappers.OrderMapper;
import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orders.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class OrderServiceImpl implements OrderService {
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final OrderItemMapper orderItemMapper;

  public OrderServiceImpl(
      OrderRepository orderRepository, OrderMapper orderMapper, OrderItemMapper orderItemMapper) {
    this.orderRepository = orderRepository;
    this.orderMapper = orderMapper;
    this.orderItemMapper = orderItemMapper;
  }

  @Override
  public List<OrderDTO> findAll() {
    return StreamSupport.stream(this.orderRepository.findAll().spliterator(), false)
        .map(this.orderMapper::toDto)
        .toList();
  }

  @Override
  public Optional<OrderDTO> findById(Long id) {
    return this.orderRepository.findById(id).map(this.orderMapper::toDto);
  }

  @Override
  public OrderDTO save() {
    return this.orderMapper.toDto(this.orderRepository.save(new Order()));
  }

  @Override
  public Optional<OrderDTO> update(Long id, OrderCreateUpdateDTO dto) {
    return this.orderRepository
        .findById(id)
        .map(
            order -> {
              order.setOrderItems(
                  dto.getOrderItems().stream().map(this.orderItemMapper::toOrderItem).toList());
              return this.orderMapper.toDto(this.orderRepository.save(order));
            });
  }

  @Override
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
