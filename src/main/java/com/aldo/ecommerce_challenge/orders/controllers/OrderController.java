package com.aldo.ecommerce_challenge.orders.controllers;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateUpdateDTO;
import com.aldo.ecommerce_challenge.orderItems.models.OrderItem;
import com.aldo.ecommerce_challenge.orders.dto.OrderCreateUpdateDTO;
import com.aldo.ecommerce_challenge.orders.dto.OrderDTO;
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
  public ResponseEntity<List<OrderDTO>> getAll() {
    return ResponseEntity.ok(this.orderService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
    return this.orderService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<OrderDTO> create() {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.orderService.save());
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderDTO> update(
      @PathVariable Long id, @RequestBody OrderCreateUpdateDTO dto) {
    return this.orderService
        .update(id, dto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<OrderDTO> delete(@PathVariable Long id) {
    return this.orderService
        .delete(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
