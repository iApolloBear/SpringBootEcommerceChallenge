package com.aldo.ecommerce_challenge.orders;

import com.aldo.ecommerce_challenge.orderItems.OrderItemsData;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orders.dto.OrderDTO;
import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.products.ProductsData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class OrdersData {
  public static Optional<OrderDTO> createOrderDTOOne() {
    return Optional.of(
        new OrderDTO(
            1L,
            new BigDecimal("2437"),
            List.of(
                OrderItemsData.createOrderItemDTOOne(), OrderItemsData.createOrderItemDTOTwo())));
  }

  public static Optional<OrderDTO> createOrderDTOTwo() {
    return Optional.of(
        new OrderDTO(
            2L,
            new BigDecimal("4874"),
            List.of(
                OrderItemsData.createOrderItemDTOThree(),
                OrderItemsData.createOrderItemDTOFour())));
  }

  public static Optional<Order> createOrderOne() {
    Order order = new Order();
    order.setId(1L);
    OrderItem orderItem1 =
        new OrderItem(1L, order, ProductsData.createProductOne().orElseThrow(), 1);
    OrderItem orderItem2 =
        new OrderItem(2L, order, ProductsData.createProductTwo().orElseThrow(), 1);
    order.setOrderItems(List.of(orderItem1, orderItem2));
    order.setTotal(new BigDecimal("2437"));
    return Optional.of(order);
  }

  public static Optional<Order> createOrderTwo() {
    Order order = new Order();
    order.setId(2L);
    OrderItem orderItem1 =
        new OrderItem(3L, order, ProductsData.createProductOne().orElseThrow(), 2);
    OrderItem orderItem2 =
        new OrderItem(4L, order, ProductsData.createProductTwo().orElseThrow(), 2);
    order.setOrderItems(List.of(orderItem1, orderItem2));
    order.setTotal(new BigDecimal("4874"));
    return Optional.of(order);
  }
}
