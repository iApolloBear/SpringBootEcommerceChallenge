package com.aldo.ecommerce_challenge.products.mappers;

import com.aldo.ecommerce_challenge.products.dto.ProductCreateDTO;
import com.aldo.ecommerce_challenge.products.dto.ProductUpdateDTO;
import com.aldo.ecommerce_challenge.products.models.Product;

public interface ProductMapper {
  ProductCreateDTO toCreateDto(Product product);

  ProductUpdateDTO toUpdateDto(Product product);

  Product toProduct(ProductCreateDTO dto);

  Product toProduct(ProductUpdateDTO dto);
}
