package com.aldo.ecommerce_challenge.orderItems.services;

import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orderItems.repositories.OrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {
  private final OrderItemRepository repository;

  public OrderItemServiceImpl(OrderItemRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<OrderItem> findAll() {
    return (List<OrderItem>) this.repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<OrderItem> findById(Long id) {
    return this.repository.findById(id);
  }

  @Override
  @Transactional
  public OrderItem save(OrderItem orderItem) {
    return this.repository.save(orderItem);
  }

  @Override
  @Transactional
  public Optional<OrderItem> update(Long id, OrderItem orderItem) {
    return this.repository
        .findById(id)
        .map(
            orderItemDb -> {
              orderItemDb.setOrder(orderItem.getOrder());
              orderItemDb.setProduct(orderItem.getProduct());
              orderItemDb.setQuantity(orderItem.getQuantity());
              orderItemDb.setPrice(
                  orderItem
                      .getProduct()
                      .getPrice()
                      .multiply(BigDecimal.valueOf(orderItem.getQuantity())));
              return this.repository.save(orderItemDb);
            });
  }

  @Override
  public Optional<OrderItem> delete(Long id) {
    return this.repository
        .findById(id)
        .map(
            orderItem -> {
              this.repository.delete(orderItem);
              return orderItem;
            });
  }
}
