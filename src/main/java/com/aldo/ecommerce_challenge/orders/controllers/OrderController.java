package com.aldo.ecommerce_challenge.orders.controllers;

import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orders.models.Order;
import com.aldo.ecommerce_challenge.orders.services.OrderService;
import com.aldo.ecommerce_challenge.products.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping
  public ResponseEntity<List<Order>> getAll() {
    return ResponseEntity.ok(this.orderService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getById(@PathVariable Long id) {
    return this.orderService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Order> create(@RequestBody List<OrderItem> orderItems) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.orderService.save(orderItems));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Order> update(
      @PathVariable Long id, @RequestBody List<OrderItem> orderItems) {
    return this.orderService
        .update(id, orderItems)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Order> delete(@PathVariable Long id) {
    return this.orderService
        .delete(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
