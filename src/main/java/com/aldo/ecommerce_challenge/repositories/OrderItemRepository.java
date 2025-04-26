package com.aldo.ecommerce_challenge.repositories;

import com.aldo.ecommerce_challenge.models.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {}
