package com.aldo.ecommerce_challenge.products.controllers;

import com.aldo.ecommerce_challenge.products.ProductsData;
import com.aldo.ecommerce_challenge.products.dto.ProductCreateDTO;
import com.aldo.ecommerce_challenge.products.dto.ProductUpdateDTO;
import com.aldo.ecommerce_challenge.products.mappers.ProductMapper;
import com.aldo.ecommerce_challenge.products.mappers.ProductMapperImpl;
import com.aldo.ecommerce_challenge.products.models.Product;
import com.aldo.ecommerce_challenge.products.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
  @Autowired private MockMvc mvc;
  @MockitoBean private ProductService productService;
  ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    objectMapper = new ObjectMapper();
  }

  @Test
  @DisplayName("Should return all products successfully")
  public void testGetAll() throws Exception {
    Product product1 = ProductsData.createProductOne().orElseThrow();
    Product product2 = ProductsData.createProductOne().orElseThrow();
    List<Product> products = Arrays.asList(product1, product2);
    when(this.productService.findAll()).thenReturn(products);
    mvc.perform(get("/api/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value(product1.getName()))
        .andExpect(jsonPath("$[1].name").value(product2.getName()))
        .andExpect(jsonPath("$[0].price").value(product1.getPrice()))
        .andExpect(jsonPath("$[1].price").value(product2.getPrice()))
        .andExpect(jsonPath("$[0].description").value(product1.getDescription()))
        .andExpect(jsonPath("$[1].description").value(product2.getDescription()))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(content().json(objectMapper.writeValueAsString(products)));
    verify(this.productService).findAll();
  }

  @Test
  @DisplayName("Should return a product by ID if found")
  public void testGetById() throws Exception {
    Product product = ProductsData.createProductOne().orElseThrow();
    when(this.productService.findById(1L)).thenReturn(Optional.of(product));

    mvc.perform(get("/api/products/{id}", 1L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(product.getName()))
        .andExpect(jsonPath("$.description").value(product.getDescription()))
        .andExpect(jsonPath("$.price").value(product.getPrice()));
    verify(this.productService).findById(1L);
  }

  @Test
  @DisplayName("Should return 404 when product by ID not found")
  void testGetProductByIdNotFound() throws Exception {
    when(productService.findById(1L)).thenReturn(Optional.empty());

    mvc.perform(get("/api/products/{id}", 1L)).andExpect(status().isNotFound());

    verify(this.productService).findById(1L);
  }

  @Test
  @DisplayName("Should create a new product successfully")
  void testCreateProduct() throws Exception {
    ProductCreateDTO dto =
        new ProductCreateDTO("Dummy", "Portishead Album", new BigDecimal("799.33"));
    Product product = ProductsData.createProductOne().orElseThrow();
    when(productService.save(dto)).thenReturn(product);

    mvc.perform(
            post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(product.getId()))
        .andExpect(jsonPath("$.name").value(product.getName()))
        .andExpect(jsonPath("$.description").value(product.getDescription()))
        .andExpect(jsonPath("$.price").value(product.getPrice()));
    verify(this.productService).save(dto);
  }

  @Test
  @DisplayName("Should not create a new product")
  void testCreateProductWithInvalidDTO() throws Exception {
    ProductCreateDTO dto = new ProductCreateDTO();
    Product product = ProductsData.createProductOne().orElseThrow();
    when(productService.save(dto)).thenReturn(product);

    mvc.perform(
            post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isBadRequest());
    verify(this.productService, never()).save(dto);
  }

  @Test
  @DisplayName("Should update an existing product successfully if found")
  void testUpdateProductFound() throws Exception {
    ProductUpdateDTO dto =
        new ProductUpdateDTO("SOUR", "Olivia Rodrigo Album", new BigDecimal("800"));
    Product product = ProductsData.createProductOne().orElseThrow();

    when(productService.update(1L, dto)).thenReturn(Optional.of(product));

    mvc.perform(
            put("/api/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(product.getId()))
        .andExpect(jsonPath("$.name").value(product.getName()))
        .andExpect(jsonPath("$.description").value(product.getDescription()))
        .andExpect(jsonPath("$.price").value(product.getPrice()));

    verify(this.productService).update(1L, dto);
  }

  @Test
  @DisplayName("Should update only one field of an existing product successfully if found")
  void testUpdateOneFieldProductFound() throws Exception {
    ProductUpdateDTO dto = new ProductUpdateDTO();
    dto.setName("SOUR");
    Product product = ProductsData.createProductOne().orElseThrow();
    product.setName(dto.getName());

    when(productService.update(1L, dto)).thenReturn(Optional.of(product));

    mvc.perform(
            put("/api/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(product.getId()))
        .andExpect(jsonPath("$.name").value("SOUR"))
        .andExpect(jsonPath("$.description").value(product.getDescription()))
        .andExpect(jsonPath("$.price").value(product.getPrice()));

    verify(this.productService).update(1L, dto);
  }

  @Test
  @DisplayName("Should return 404 when updating a non-existing product")
  void testUpdateProductNotFound() throws Exception {
    ProductUpdateDTO dto =
        new ProductUpdateDTO("SOUR", "Olivia Rodrigo Album", new BigDecimal("800"));

    when(productService.update(1L, dto)).thenReturn(Optional.empty());

    mvc.perform(
            put("/api/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isNotFound());

    verify(this.productService).update(1L, dto);
  }

  @Test
  @DisplayName("Should delete an existing product successfully")
  void testDeleteProductFound() throws Exception {
    Product product = ProductsData.createProductTwo().orElseThrow();

    when(productService.delete(2L)).thenReturn(Optional.of(product));

    mvc.perform(delete("/api/products/{id}", 2L))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(product.getId()))
        .andExpect(jsonPath("$.name").value(product.getName()))
        .andExpect(jsonPath("$.description").value(product.getDescription()))
        .andExpect(jsonPath("$.price").value(product.getPrice()));

    verify(this.productService).delete(2L);
  }

  @Test
  @DisplayName("Should return 404 when deleting a non-existing product")
  void testDeleteProductNotFound() throws Exception {
    when(productService.delete(1L)).thenReturn(Optional.empty());

    mvc.perform(delete("/api/products/{id}", 1L)).andExpect(status().isNotFound());

    verify(this.productService).delete(1L);
  }
}
