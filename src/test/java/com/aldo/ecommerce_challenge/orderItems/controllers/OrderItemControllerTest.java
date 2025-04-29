package com.aldo.ecommerce_challenge.orderItems.controllers;

import com.aldo.ecommerce_challenge.orderItems.OrderItemsData;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemCreateDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemDTO;
import com.aldo.ecommerce_challenge.orderItems.dto.OrderItemUpdateDTO;
import com.aldo.ecommerce_challenge.orderItems.services.OrderItemService;

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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderItemController.class)
@DisplayName("OrderItemController Tests")
class OrderItemControllerTest {
  @Autowired private MockMvc mvc;
  @MockitoBean private OrderItemService orderItemService;
  ObjectMapper objectMapper;

  @BeforeEach
  void setup() {
    objectMapper = new ObjectMapper();
  }

  @Test
  @DisplayName("Should return all order items")
  void testGetAll() throws Exception {
    OrderItemDTO orderItemDTO1 = OrderItemsData.createOrderItemDTOOne();
    OrderItemDTO orderItemDTO2 = OrderItemsData.createOrderItemDTOTwo();
    when(orderItemService.findAll()).thenReturn(List.of(orderItemDTO1, orderItemDTO2));

    mvc.perform(get("/api/order-items"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(orderItemDTO1.getId()))
        .andExpect(jsonPath("$[1].id").value(orderItemDTO2.getId()))
        .andExpect(jsonPath("$[0].orderId").value(orderItemDTO1.getOrderId()))
        .andExpect(jsonPath("$[1].orderId").value(orderItemDTO2.getOrderId()))
        .andExpect(jsonPath("$[0].productId").value(orderItemDTO1.getProductId()))
        .andExpect(jsonPath("$[1].productId").value(orderItemDTO2.getProductId()))
        .andExpect(jsonPath("$[0].quantity").value(orderItemDTO1.getQuantity()))
        .andExpect(jsonPath("$[1].quantity").value(orderItemDTO2.getQuantity()))
        .andExpect(jsonPath("$", hasSize(2)));

    verify(this.orderItemService).findAll();
  }

  @Test
  @DisplayName("Should return an order item by ID")
  void testGetById() throws Exception {
    OrderItemDTO dto = OrderItemsData.createOrderItemDTOOne();
    when(orderItemService.findById(1L)).thenReturn(Optional.of(dto));

    mvc.perform(get("/api/order-items/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(dto.getId()))
        .andExpect(jsonPath("$.orderId").value(dto.getOrderId()))
        .andExpect(jsonPath("$.productId").value(dto.getProductId()))
        .andExpect(jsonPath("$.quantity").value(dto.getQuantity()));

    verify(this.orderItemService).findById(1L);
  }

  @Test
  @DisplayName("Should throw 404 when order item not found by ID")
  void testGetByIdNotFound() throws Exception {
    when(orderItemService.findById(99L)).thenReturn(Optional.empty());

    mvc.perform(get("/api/order-items/99")).andExpect(status().isNotFound());

    verify(this.orderItemService).findById(99L);
  }

  @Test
  @DisplayName("Should create a new order item")
  void testCreate() throws Exception {
    OrderItemCreateDTO createDTO = OrderItemsData.createOrderItemCreateDTO();
    OrderItemDTO dto = OrderItemsData.createOrderItemDTOOne();
    when(orderItemService.save(createDTO)).thenReturn(dto);

    mvc.perform(
            post("/api/order-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(dto.getId()))
        .andExpect(jsonPath("$.productId").value(dto.getProductId()))
        .andExpect(jsonPath("$.orderId").value(dto.getOrderId()))
        .andExpect(jsonPath("$.quantity").value(dto.getQuantity()));

    verify(this.orderItemService).save(createDTO);
  }

  @Test
  @DisplayName("Should not create a new order item")
  void testCreateWithInvalidDTO() throws Exception {
    OrderItemCreateDTO createDTO = new OrderItemCreateDTO(null, null, 2);
    OrderItemDTO dto = OrderItemsData.createOrderItemDTOOne();
    when(orderItemService.save(createDTO)).thenReturn(dto);

    mvc.perform(
            post("/api/order-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
        .andExpect(status().isBadRequest());

    verify(this.orderItemService, never()).save(createDTO);
  }

  @Test
  @DisplayName("Should update an existing order item successfully if found")
  void testUpdateOrderItemFound() throws Exception {
    OrderItemUpdateDTO dto = new OrderItemUpdateDTO();
    dto.setQuantity(3);
    dto.setOrderId(2L);
    dto.setProductId(2L);
    OrderItemDTO orderItem = OrderItemsData.createOrderItemDTOOne();
    orderItem.setQuantity(3);
    orderItem.setOrderId(2L);
    orderItem.setProductId(2L);

    when(orderItemService.update(1L, dto)).thenReturn(Optional.of(orderItem));

    mvc.perform(
            put("/api/order-items/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(orderItem.getId()))
        .andExpect(jsonPath("$.quantity").value(3))
        .andExpect(jsonPath("$.orderId").value(2))
        .andExpect(jsonPath("$.productId").value(2))
        .andExpect(jsonPath("$.price").value(orderItem.getPrice()));

    verify(this.orderItemService).update(1L, dto);
  }

  @Test
  @DisplayName("Should update only one field of an existing order item successfully if found")
  void testUpdateOneFieldOrderItemFound() throws Exception {
    OrderItemUpdateDTO dto = new OrderItemUpdateDTO();
    dto.setQuantity(0);
    OrderItemDTO orderItem = OrderItemsData.createOrderItemDTOOne();
    orderItem.setQuantity(0);

    when(orderItemService.update(1L, dto)).thenReturn(Optional.of(orderItem));

    mvc.perform(
            put("/api/order-items/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(orderItem.getId()))
        .andExpect(jsonPath("$.quantity").value(0))
        .andExpect(jsonPath("$.orderId").value(orderItem.getOrderId()))
        .andExpect(jsonPath("$.productId").value(orderItem.getProductId()))
        .andExpect(jsonPath("$.price").value(orderItem.getPrice()));

    verify(this.orderItemService).update(1L, dto);
  }

  @Test
  @DisplayName("Should return 404 when updating a non-existing order item")
  void testUpdateOrderItemNotFound() throws Exception {
    OrderItemUpdateDTO dto = new OrderItemUpdateDTO();

    when(orderItemService.update(1L, dto)).thenReturn(Optional.empty());

    mvc.perform(
            put("/api/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isNotFound());

    verify(this.orderItemService, never()).update(1L, dto);
  }

  @Test
  @DisplayName("Should delete an existing product successfully")
  void testDeleteProductFound() throws Exception {
    OrderItemDTO orderItemDTO = OrderItemsData.createOrderItemDTOOne();

    when(orderItemService.delete(1L)).thenReturn(Optional.of(orderItemDTO));

    mvc.perform(delete("/api/order-items/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(orderItemDTO.getId()))
        .andExpect(jsonPath("$.productId").value(orderItemDTO.getProductId()))
        .andExpect(jsonPath("$.price").value(orderItemDTO.getPrice()))
        .andExpect(jsonPath("$.orderId").value(orderItemDTO.getOrderId()))
        .andExpect(jsonPath("$.quantity").value(orderItemDTO.getQuantity()));

    verify(this.orderItemService).delete(1L);
  }

  @Test
  @DisplayName("Should return 404 when deleting a non-existing product")
  void testDeleteProductNotFound() throws Exception {
    when(orderItemService.delete(1L)).thenReturn(Optional.empty());

    mvc.perform(delete("/api/order-items/{id}", 1L)).andExpect(status().isNotFound());

    verify(this.orderItemService).delete(1L);
  }
}
