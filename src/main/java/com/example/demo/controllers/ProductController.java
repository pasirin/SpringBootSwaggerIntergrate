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
@RequestMapping("/product")
public class ProductController {
  @Autowired ProductService productService;


  //All Can Search For Product
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok().body(productService.getAll());
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
  @GetMapping("/{id}")
  public ResponseEntity<?> findProduct(@Valid @PathVariable Long id) {
    return ResponseEntity.ok().body(productService.getById(id));
  }

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
  @GetMapping("/search")
  public ResponseEntity<?> searchProduct(@Valid @RequestParam String keyword) {
    return ResponseEntity.ok().body(productService.getByName(keyword));
  }

  // Admin Section Only Admin Can add/delete/modify
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("admin/add")
  public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest productRequest) {
    MessageResponse output = productService.create(productRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("admin/modify/{id}")
  public ResponseEntity<?> changeProduct(
      @Valid @PathVariable Long id, @RequestBody ProductRequest productRequest) {
    MessageResponse output = productService.update(id, productRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("admin/delete/{id}")
  public ResponseEntity<?> deleteProduct(@Valid @PathVariable Long id) {
    MessageResponse output = productService.delete(id);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }
}
