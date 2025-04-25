package com.aldo.ecommerce_challenge.services.productService;

import com.aldo.ecommerce_challenge.models.Product;
import com.aldo.ecommerce_challenge.repositories.ProductRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
  private final ProductRepository repository;

  public ProductServiceImpl(ProductRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Product> findAll() {
    return (List<Product>) this.repository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Product> findById(Long id) {
    return this.repository.findById(id);
  }

  @Override
  @Transactional
  public Product save(Product product) {
    return this.repository.save(product);
  }

  @Override
  public Optional<Product> update(Long id, Product product) {
    return repository
        .findById(id)
        .map(
            productDb -> {
              productDb.setName(product.getName());
              productDb.setDescription(product.getDescription());
              productDb.setPrice(product.getPrice());
              return this.repository.save(productDb);
            });
  }

  @Override
  public Optional<Product> delete(Long id) {
    return repository
        .findById(id)
        .map(
            product -> {
              this.repository.delete(product);
              return product;
            });
  }
}
