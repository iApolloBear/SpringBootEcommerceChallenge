package com.aldo.ecommerce_challenge.products.controllers;

import com.aldo.ecommerce_challenge.products.ProductsData;
import com.aldo.ecommerce_challenge.products.models.Product;
import com.aldo.ecommerce_challenge.products.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
  @Autowired private MockMvc mvc;
  @MockitoBean private ProductService service;
  ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  public void getAll() throws Exception {
    List<Product> products =
        Arrays.asList(
            ProductsData.createProductOne().orElseThrow(),
            ProductsData.createProductTwo().orElseThrow());
    when(this.service.findAll()).thenReturn(products);
    mvc.perform(get("/api/products").contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].name").value("GUTS"))
        .andExpect(jsonPath("$[1].name").value("F-1 Trillion"))
        .andExpect(jsonPath("$[0].price").value("938"))
        .andExpect(jsonPath("$[1].price").value("1499"))
        .andExpect(jsonPath("$[0].description").value("Olivia Rodrigo Album"))
        .andExpect(jsonPath("$[1].description").value("Post Malone Album"))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(content().json(objectMapper.writeValueAsString(products)));
  }

  @Test
  public void getById() throws Exception {
    when(this.service.findById(1L)).thenReturn(ProductsData.createProductOne());

    mvc.perform(get("/api/products/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("GUTS"))
        .andExpect(jsonPath("$.description").value("Olivia Rodrigo Album"))
        .andExpect(jsonPath("$.price").value("938"));
    verify(this.service).findById(1L);
  }

  @Test
  public void create() throws Exception {
    Product product = new Product("Dummy", "Portishead Album", new BigDecimal("799.33"));
    when(this.service.save(any()))
        .then(
            invocation -> {
              Product p = invocation.getArgument(0);
              p.setId(3L);
              return p;
            });

    mvc.perform(
            post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(3)))
        .andExpect(jsonPath("$.name", is("Dummy")))
        .andExpect(jsonPath("$.description", is("Portishead Album")))
        .andExpect(jsonPath("$.price", is(799.33)));
    verify(this.service).save(any());
  }

  @Test
  public void update() throws Exception {
    Product originalProduct = ProductsData.createProductOne().orElseThrow();
    Product newProduct = new Product(1L, "SOUR", "Olivia Rodrigo Album", new BigDecimal("800"));
    when(this.service.update(1L, originalProduct)).thenReturn(Optional.of(newProduct));

    mvc.perform(
            put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(originalProduct)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.name", is("SOUR")))
        .andExpect(jsonPath("$.description", is("Olivia Rodrigo Album")))
        .andExpect(jsonPath("$.price", is(800)));
    verify(this.service).update(1L, originalProduct);
  }

  @Test
  public void delete() throws Exception {
    when(this.service.delete(2L)).thenReturn(ProductsData.createProductTwo());

    mvc.perform(MockMvcRequestBuilders.delete("/api/products/2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.name").value("F-1 Trillion"))
        .andExpect(jsonPath("$.description").value("Post Malone Album"))
        .andExpect(jsonPath("$.price").value(1499));
  }
}
