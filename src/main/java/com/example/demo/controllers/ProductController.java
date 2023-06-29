package com.example.demo.controllers;

import com.example.demo.payload.request.ProductRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/product")
public class ProductController {
  @Autowired ProductService productService;

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok().body(productService.getAll());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/add")
  public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest productRequest) {
    MessageResponse output = productService.create(productRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/modify/{id}")
  public ResponseEntity<?> changeProduct(
      @Valid @PathVariable Long id, @RequestBody ProductRequest productRequest) {
    MessageResponse output = productService.update(id, productRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteProduct(@Valid @PathVariable Long id) {
    MessageResponse output = productService.delete(id);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
  @GetMapping("/{id}")
  public ResponseEntity<?> findProduct(@Valid @PathVariable Long id) {
    return ResponseEntity.ok().body(productService.getById(id));
  }
}
