package com.aldo.ecommerce_challenge.orders.controllers;

import com.aldo.ecommerce_challenge.orders.OrdersData;
import com.aldo.ecommerce_challenge.orders.dto.OrderDTO;
import com.aldo.ecommerce_challenge.orders.dto.OrderUpdateDTO;
import com.aldo.ecommerce_challenge.orders.services.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@DisplayName("OrderController Tests")
class OrderControllerTest {
  @Autowired private MockMvc mvc;
  @MockitoBean private OrderService orderService;
  ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  @DisplayName("Should return all orders")
  void testGetAll() throws Exception {
    OrderDTO orderDTO1 = OrdersData.createOrderDTOOne().orElseThrow();
    OrderDTO orderDTO2 = OrdersData.createOrderDTOTwo().orElseThrow();
    List<OrderDTO> orders = List.of(orderDTO1, orderDTO2);
    when(orderService.findAll()).thenReturn(List.of(orderDTO1, orderDTO2));

    mvc.perform(get("/api/orders"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(orderDTO1.getId()))
        .andExpect(jsonPath("$[1].id").value(orderDTO2.getId()))
        .andExpect(jsonPath("$[0].total").value(orderDTO1.getTotal()))
        .andExpect(jsonPath("$[1].total").value(orderDTO2.getTotal()))
        .andExpect(jsonPath("$[0].orderItems", hasSize(orderDTO1.getOrderItems().size())))
        .andExpect(jsonPath("$[1].orderItems", hasSize(orderDTO2.getOrderItems().size())))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(content().json(objectMapper.writeValueAsString(orders)));
    verify(this.orderService).findAll();
  }

  @Test
  @DisplayName("Should return an order by ID")
  void testGetById() throws Exception {
    Optional<OrderDTO> order = OrdersData.createOrderDTOOne();
    when(orderService.findById(1L)).thenReturn(order);

    mvc.perform(get("/api/orders/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.total").value(order.get().getTotal()))
        .andExpect(jsonPath("$.orderItems", hasSize(2)));

    verify(this.orderService).findById(1L);
  }

  @Test
  @DisplayName("Should throw 404 when order not found by ID")
  void testGetByIdNotFound() throws Exception {
    when(orderService.findById(999L)).thenReturn(Optional.empty());

    mvc.perform(get("/api/orders/{id}", 999L)).andExpect(status().isNotFound());
    verify(this.orderService).findById(999L);
  }

  @Test
  @DisplayName("Should create a new order")
  void testCreate() throws Exception {
    OrderDTO order = OrdersData.createOrderDTOOne().orElseThrow();
    when(orderService.save()).thenReturn(order);

    mvc.perform(post("/api/orders"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.total").value(order.getTotal()))
        .andExpect(jsonPath("$.orderItems", hasSize(2)));

    verify(this.orderService).save();
  }

  @Test
  @DisplayName("Should update an existing order")
  void testUpdate() throws Exception {
    OrderUpdateDTO updateDTO = new OrderUpdateDTO(List.of(1L, 2L));
    OrderDTO order = OrdersData.createOrderDTOOne().orElseThrow();
    when(orderService.update(1L, updateDTO)).thenReturn(Optional.of(order));

    mvc.perform(
            put("/api/orders/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(order.getId()))
        .andExpect(jsonPath("$.total").value(order.getTotal()))
        .andExpect(jsonPath("$.orderItems", hasSize(2)));
    verify(this.orderService).update(1L, updateDTO);
  }

  @Test
  @DisplayName("Should throw 404 when updating non-existing order")
  void testUpdateNotFound() throws Exception {
    OrderUpdateDTO updateDTO = new OrderUpdateDTO(List.of(1L, 2L));
    when(orderService.update(999L, updateDTO)).thenReturn(Optional.empty());

    mvc.perform(
            put("/api/orders/{id}", 999L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
        .andExpect(status().isNotFound());
    verify(this.orderService).update(999L, updateDTO);
  }

  @Test
  @DisplayName("Should delete an order")
  void testDelete() throws Exception {
    OrderDTO order = OrdersData.createOrderDTOOne().orElseThrow();
    when(orderService.delete(1L)).thenReturn(Optional.of(order));

    mvc.perform(delete("/api/orders/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.total").value(order.getTotal()))
        .andExpect(jsonPath("$.orderItems", hasSize(2)));
    verify(this.orderService).delete(1L);
  }

  @Test
  @DisplayName("Should throw 404 when deleting non-existing order")
  void testDeleteNotFound() throws Exception {
    when(orderService.delete(999L)).thenReturn(Optional.empty());

    mvc.perform(delete("/api/orders/{id}", 999L)).andExpect(status().isNotFound());
    verify(this.orderService).delete(999L);
  }
}
