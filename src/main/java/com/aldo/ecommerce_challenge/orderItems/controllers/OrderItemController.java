package com.aldo.ecommerce_challenge.orderItems.controllers;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateUpdateDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.services.OrderItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@Tag(name = "Order Items", description = "Operations related to order items")
public class OrderItemController {
  private final OrderItemService orderItemService;

  public OrderItemController(OrderItemService orderItemService) {
    this.orderItemService = orderItemService;
  }

  @GetMapping
  public ResponseEntity<List<OrderItemDTO>> getAll() {
    return ResponseEntity.ok(this.orderItemService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderItemDTO> getById(@PathVariable Long id) {
    return this.orderItemService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<OrderItemDTO> create(@RequestBody OrderItemCreateUpdateDTO orderItem) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.orderItemService.save(orderItem));
  }

  @PutMapping("/{id}")
  public ResponseEntity<OrderItemDTO> update(
      @PathVariable Long id, @RequestBody OrderItemCreateUpdateDTO dto) {
    return this.orderItemService
        .update(id, dto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<OrderItemDTO> delete(@PathVariable Long id) {
    return this.orderItemService
        .delete(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
