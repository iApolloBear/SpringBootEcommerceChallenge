package com.aldo.ecommerce_challenge.orders.services;

import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orders.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
  private final OrderRepository repository;

  public OrderServiceImpl(OrderRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Order> findAll() {
    return (List<Order>) this.repository.findAll();
  }

  @Override
  public Optional<Order> findById(Long id) {
    return this.repository.findById(id);
  }

  @Override
  public Order save(List<OrderItem> orderItems) {
    Order order = new Order();
    if (!orderItems.isEmpty()) {
      order.setOrderItems(orderItems);
    }
    return this.repository.save(order);
  }

  @Override
  public Optional<Order> update(Long id, List<OrderItem> orderItems) {
    return this.repository
        .findById(id)
        .map(
            orderDb -> {
              orderDb.setOrderItems(orderItems);
              return this.repository.save(orderDb);
            });
  }

  @Override
  public Optional<Order> delete(Long id) {
    return repository
        .findById(id)
        .map(
            product -> {
              this.repository.delete(product);
              return product;
            });
  }
}
