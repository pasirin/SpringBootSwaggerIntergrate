package com.example.demo.services;

import com.example.demo.models.Product;
import com.example.demo.payload.request.ProductRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ProductService implements Service<ProductRequest, Product> {
  @Autowired ProductRepository productRepository;

  @Override
  public MessageResponse create(ProductRequest productRequest) {
    if (productRepository.existsByName(productRequest.getName())) {
      return MessageResponse.body("Error: There's already a product with the same name");
    }
    Product product =
        new Product(
            productRequest.getName(), productRequest.getCategory(), productRequest.getPrice());
    productRepository.save(product);
    return MessageResponse.status(true); // .setMessage("Successfully Add Product to the Database");
  }

  @Override
  public MessageResponse update(Long id, ProductRequest productRequest) {
    if (!productRepository.existsById(id)) {
      return MessageResponse.body("There Are No Product with the id: " + id);
    } else {
      Product product =
          productRepository
              .findById(id)
              .orElseThrow(
                  () -> new RuntimeException("ERROR: There are no product with the id: " + id));
      product.setName(productRequest.getName());
      product.setCategory(productRequest.getCategory());
      product.setPrice(productRequest.getPrice());
      productRepository.save(product);
      return MessageResponse.status(true);
      // .setMessage("The Product With the id: " + id + " is updated");
    }
  }

  @Override
  public MessageResponse delete(Long id) {
    if (!productRepository.existsById(id)) {
      return MessageResponse.body("There Are No Product with the id: " + id);
    } else {
      productRepository.deleteById(id);
      return MessageResponse.status(true);
      // .setMessage("The Product With the id: " + id + " is deleted");
    }
  }

  @Override
  public Collection<Product> getAll() {
    return productRepository.findAll();
  }

  @Override
  public Optional<Product> getById(Long id) {
    if (!productRepository.existsById(id)) {
      return Optional.empty();
    } else {
      return productRepository.findById(id);
    }
  }

  public List<Product> getByName(String name) {
    return productRepository.findByNameLike("%" + name + "%");
  }
}
