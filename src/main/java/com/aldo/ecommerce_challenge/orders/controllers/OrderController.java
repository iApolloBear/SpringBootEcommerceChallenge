package com.aldo.ecommerce_challenge.orders.controllers;

import com.aldo.ecommerce_challenge.orders.dto.OrderUpdateDTO;
import com.aldo.ecommerce_challenge.orders.dto.OrderDTO;
import com.aldo.ecommerce_challenge.orders.exceptions.OrderNotFoundException;
import com.aldo.ecommerce_challenge.orders.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Operations related to orders")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @Operation(
      summary = "Find all orders",
      description = "Get all orders in the database.",
      responses = {@ApiResponse(responseCode = "200", description = "List of all orders")})
  @GetMapping
  public ResponseEntity<List<OrderDTO>> getAll() {
    return ResponseEntity.ok(this.orderService.findAll());
  }

  @Operation(
      summary = "Find an order by ID",
      description = "Retrieve a specific order by its ID.",
      responses = {
        @ApiResponse(responseCode = "200", description = "Order found"),
        @ApiResponse(responseCode = "404", description = "Order not found")
      })
  @GetMapping("/{id}")
  public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
    return this.orderService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new OrderNotFoundException(id));
  }

  @Operation(
      summary = "Create a new order",
      description = "Create a new order and store it in the database.",
      responses = {
        @ApiResponse(responseCode = "201", description = "Order created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
      })
  @PostMapping
  public ResponseEntity<OrderDTO> create() {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.orderService.save());
  }

  @Operation(
      summary = "Update an existing order",
      description = "Update the order fields by its ID.",
      responses = {
        @ApiResponse(responseCode = "200", description = "Order updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Order not found")
      })
  @PutMapping("/{id}")
  public ResponseEntity<OrderDTO> update(@PathVariable Long id, @RequestBody OrderUpdateDTO dto) {
    return this.orderService
        .update(id, dto)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new OrderNotFoundException(id));
  }

  @Operation(
      summary = "Delete an order",
      description = "Delete the order by its ID from the database.",
      responses = {
        @ApiResponse(responseCode = "200", description = "Order deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Order not found")
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<OrderDTO> delete(@PathVariable Long id) {
    return this.orderService
        .delete(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new OrderNotFoundException(id));
  }
}
