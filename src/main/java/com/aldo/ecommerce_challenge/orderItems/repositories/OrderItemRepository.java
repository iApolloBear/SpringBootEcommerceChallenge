package com.aldo.ecommerce_challenge.orderItems.repositories;

import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {}
