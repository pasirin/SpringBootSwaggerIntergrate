package com.example.demo.controllers;

import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/product")
public class PublicController {
  @Autowired ProductService productService;

  // All Can Search For Product
  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok().body(productService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> findProduct(@Valid @PathVariable Long id) {
    return ResponseEntity.ok().body(productService.getById(id));
  }

  @GetMapping("/search")
  public ResponseEntity<?> searchProduct(@Valid @RequestParam String keyword) {
    return ResponseEntity.ok().body(productService.getByName(keyword));
  }
}
