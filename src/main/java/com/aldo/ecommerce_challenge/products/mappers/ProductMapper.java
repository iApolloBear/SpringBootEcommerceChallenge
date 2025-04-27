package com.aldo.ecommerce_challenge.products.mappers;

import com.aldo.ecommerce_challenge.products.dto.ProductCreateUpdateDTO;
import com.aldo.ecommerce_challenge.products.models.Product;

public interface ProductMapper {
  ProductCreateUpdateDTO toDto(Product product);

  Product toProduct(ProductCreateUpdateDTO dto);
}
