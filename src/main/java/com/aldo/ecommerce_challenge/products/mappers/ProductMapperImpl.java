package com.aldo.ecommerce_challenge.products.mappers;

import com.aldo.ecommerce_challenge.products.dto.ProductCreateDTO;
import com.aldo.ecommerce_challenge.products.dto.ProductUpdateDTO;
import com.aldo.ecommerce_challenge.products.models.Product;

import java.util.Optional;

public class ProductMapperImpl implements ProductMapper {
  @Override
  public ProductCreateDTO toCreateDto(Product product) {
    return new ProductCreateDTO(product.getName(), product.getDescription(), product.getPrice());
  }

  @Override
  public ProductUpdateDTO toUpdateDto(Product product) {
    ProductUpdateDTO dto = new ProductUpdateDTO();
    dto.setName(Optional.of(product.getName()));
    dto.setDescription(Optional.of(product.getDescription()));
    dto.setPrice(Optional.of(product.getPrice()));
    return dto;
  }

  @Override
  public Product toProduct(ProductCreateDTO dto) {
    return new Product(dto.getName(), dto.getDescription(), dto.getPrice());
  }

  @Override
  public Product toProduct(ProductUpdateDTO dto) {
    return new Product(
        dto.getName().orElse(null), dto.getDescription().orElse(null), dto.getPrice().orElse(null));
  }
}
