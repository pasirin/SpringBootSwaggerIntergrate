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
@RequestMapping("/order")
public class OrderController {
  @Autowired OrderService orderService;

  // All Can Add
  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @PostMapping("/add")
  public ResponseEntity<?> addOrder(@Valid @RequestBody OrderRequest orderRequest) {
    MessageResponse output = orderService.create(orderRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  // Reduce The Scope Of User
  @PreAuthorize("hasRole('MODERATOR')")
  @GetMapping("customer/{customerId}/all")
  public ResponseEntity<?> getAllCustomerOder(@Valid @PathVariable Long customerId) {
    return ResponseEntity.ok().body(orderService.getByCustomerId(customerId));
  }

  @PreAuthorize("hasRole('MODERATOR')")
  @PutMapping("customer/{customerId}/modify/{id}")
  public ResponseEntity<?> changeCustomerOrder(
      @Valid @PathVariable("customerId") Long customerId,
      @PathVariable("id") Long id,
      @RequestBody OrderRequest orderRequest) {
    orderRequest.setCustomerId(customerId);
    MessageResponse output = orderService.update(id, orderRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('MODERATOR')")
  @GetMapping("customer/{customerId}/{id}")
  public ResponseEntity<?> getOrderByCusIdAndID(
      @Valid @PathVariable("customerId") Long customerId, @PathVariable("id") Long id) {
    return ResponseEntity.ok().body(orderService.getByCustomerIdAndOrderId(customerId, id));
  }

  @PreAuthorize("hasRole('MODERATOR')")
  @DeleteMapping("customer/{customerId}/delete/{id}")
  public ResponseEntity<?> deleteByCusIdAndID(
      @Valid @PathVariable("customerId") Long customerId, @PathVariable("id") Long id) {
    MessageResponse output = orderService.deleteByCustomerIdAndOrderId(customerId, id);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  // Admin Section
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("admin/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok().body(orderService.getAll());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("admin/modify/{id}")
  public ResponseEntity<?> changeOrder(
      @Valid @PathVariable Long id, @RequestBody OrderRequest orderRequest) {
    MessageResponse output = orderService.update(id, orderRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("admin/delete/{id}")
  public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id) {
    MessageResponse output = orderService.delete(id);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("admin/{id}")
  public ResponseEntity<?> getById(@Valid @PathVariable Long id) {
    return ResponseEntity.ok().body(orderService.getById(id));
  }
}
