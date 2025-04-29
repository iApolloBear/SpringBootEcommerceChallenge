package com.aldo.ecommerce_challenge.orderItems.controllers;

import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemUpdateDTO;
import com.aldo.ecommerce_challenge.orderItems.exceptions.OrderItemNotFoundException;
import com.aldo.ecommerce_challenge.orderItems.services.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

  @Operation(
      summary = "Find all order items",
      description = "Fetches all order items from the database.",
      responses = {@ApiResponse(responseCode = "200", description = "Order items found")})
  @GetMapping
  public ResponseEntity<List<OrderItemDTO>> getAll() {
    return ResponseEntity.ok(this.orderItemService.findAll());
  }

  @Operation(
      summary = "Find an order item",
      description = "Finds an order item by its ID.",
      responses = {
        @ApiResponse(responseCode = "200", description = "Order item found"),
      })
  @GetMapping("/{id}")
  public ResponseEntity<OrderItemDTO> getById(@PathVariable Long id) {
    return this.orderItemService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new OrderItemNotFoundException(id));
  }

  @Operation(
      summary = "Create a new order item",
      description = "Creates a new order item in the database.",
      responses = {
        @ApiResponse(responseCode = "201", description = "Order item created successfully"),
      })
  @PostMapping
  public ResponseEntity<OrderItemDTO> create(@Valid @RequestBody OrderItemCreateDTO orderItem) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.orderItemService.save(orderItem));
  }

  @Operation(
      summary = "Update an existing order item",
      description = "Updates an order item by its ID.",
      responses = {
        @ApiResponse(responseCode = "200", description = "Order item updated successfully"),
      })
  @PutMapping("/{id}")
  public ResponseEntity<OrderItemDTO> update(
      @PathVariable Long id, @Valid @RequestBody OrderItemUpdateDTO dto) {
    return this.orderItemService
        .update(id, dto)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new OrderItemNotFoundException(id));
  }

  @Operation(
      summary = "Delete an order item",
      description = "Deletes an order item by its ID.",
      responses = {
        @ApiResponse(responseCode = "200", description = "Order item deleted successfully"),
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<OrderItemDTO> delete(@PathVariable Long id) {
    return this.orderItemService
        .delete(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new OrderItemNotFoundException(id));
  }
}
