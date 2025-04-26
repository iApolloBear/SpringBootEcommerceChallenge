package com.aldo.ecommerce_challenge.services.orderService;

import com.aldo.ecommerce_challenge.models.Order;
import com.aldo.ecommerce_challenge.models.OrderItem;
import com.aldo.ecommerce_challenge.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersServiceImplementation implements OrderService {
  private final OrderRepository repository;

  public OrdersServiceImplementation(OrderRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Order> findAll() {
    return (List<Order>) this.repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Order> findById(Long id) {
    return this.repository.findById(id);
  }

  @Override
  @Transactional
  public Order save(Order order) {
    return this.repository.save(order);
  }

  @Override
  @Transactional
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
  @Transactional
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
