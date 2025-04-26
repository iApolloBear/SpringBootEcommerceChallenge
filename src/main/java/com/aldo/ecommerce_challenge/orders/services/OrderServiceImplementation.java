package com.aldo.ecommerce_challenge.orders.services;

import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orders.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService {
  private final OrderRepository repository;

  public OrderServiceImplementation(OrderRepository repository) {
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
  public Order save(Order order) {
    return this.repository.save(order);
  }

  @Override
  public Optional<Order> update(Long id, Order order) {
    return this.repository
        .findById(id)
        .map(
            orderDb -> {
              orderDb.setCreatedAt(order.getCreatedAt());
              orderDb.setOrderItems(order.getOrderItems());
              orderDb.setTotal(
                  order.getOrderItems().stream()
                      .map(OrderItem::getPrice)
                      .reduce(BigDecimal.ZERO, BigDecimal::add));
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
