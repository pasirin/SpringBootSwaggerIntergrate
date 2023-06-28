package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.payload.request.ProductRequest;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {
  @Autowired ProductRepository productRepository;

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
  @GetMapping("/all")
  public List<Product> getAll() {
    return productRepository.findAll();
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/add")
  public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest productRequest) {
    if (productRepository.existsByName(productRequest.getName())) {
      return ResponseEntity.badRequest()
          .body("Error: There's already a product with the same name");
    }
    Product product =
        new Product(
            productRequest.getName(), productRequest.getCategory(), productRequest.getPrice());
    productRepository.save(product);

    return ResponseEntity.ok().body("Successfully Add Product to the Database");
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/modify/{id}")
  public ResponseEntity<?> changeProduct(
      @Valid @PathVariable Long id, @RequestBody ProductRequest productRequest) {
    if (productRepository.findById(id).isEmpty()) {
      return ResponseEntity.badRequest().body("There Are No Product with the id: " + id);
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
      return ResponseEntity.ok().body("The Product With the id: " + id + " is updated");
    }
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteProduct(@Valid @PathVariable Long id) {
    if (productRepository.findById(id).isEmpty()) {
      return ResponseEntity.badRequest().body("There Are No Product with the id: " + id);
    } else {
      productRepository.deleteById(id);
      return ResponseEntity.ok().body("The Product With the id: " + id + " is deleted");
    }
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
  @GetMapping("/{id}")
  public ResponseEntity<?> findProduct(@Valid @PathVariable Long id) {
    if (!productRepository.existsById(id)) {
      return ResponseEntity.badRequest().body("The Requested Product Doesn't Exist");
    } else {
      return ResponseEntity.ok().body(productRepository.findById(id));
    }
  }
}
