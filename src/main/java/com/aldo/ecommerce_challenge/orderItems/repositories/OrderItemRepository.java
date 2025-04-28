package com.aldo.ecommerce_challenge.orderItems.repositories;

import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
  List<OrderItem> findByIdIn(List<Long> ids);
}
