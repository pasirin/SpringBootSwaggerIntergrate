package com.example.demo.controllers;

import com.example.demo.payload.request.OrderRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {
  @Autowired OrderService orderService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok().body(orderService.getAll());
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @PostMapping("/add")
  public ResponseEntity<?> addOrder(@Valid @RequestBody OrderRequest orderRequest) {
    MessageResponse output = orderService.create(orderRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @PutMapping("/modify/{id}")
  public ResponseEntity<?> changeOrder(
      @Valid @PathVariable Long id, @RequestBody OrderRequest orderRequest) {
    MessageResponse output = orderService.update(id, orderRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id) {
    MessageResponse output = orderService.delete(id);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@Valid @PathVariable Long id) {
    return ResponseEntity.ok().body(orderService.getById(id));
  }
}
