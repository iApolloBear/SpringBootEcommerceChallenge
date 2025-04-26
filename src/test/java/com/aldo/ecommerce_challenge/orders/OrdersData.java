package com.aldo.ecommerce_challenge.orders;

import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.products.ProductsData;
import com.aldo.ecommerce_challenge.products.models.Product;

import java.util.Optional;

public class OrdersData {
  public static Optional<Order> createOrderOne() {
    Order order = new Order();
    order.setId(1L);
    Product product1 = ProductsData.createProductOne().orElseThrow();
    Product product2 = ProductsData.createProductTwo().orElseThrow();
    OrderItem orderItem1 = new OrderItem(order, product1, 2);
    OrderItem orderItem2 = new OrderItem(order, product2, 3);
    order.addItem(orderItem1);
    order.addItem(orderItem2);
    return Optional.of(order);
  }

  public static Optional<Order> createOrderTwo() {
    Order order = new Order();
    order.setId(2L);
    return Optional.of(order);
  }
}
