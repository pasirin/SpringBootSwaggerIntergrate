package com.example.demo.controllers;

import com.example.demo.payload.request.CustomerRequest;
import com.example.demo.payload.request.OrderRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.services.CustomerService;
import com.example.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//TODO
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/moderator")
public class ModeratorController {
  @Autowired CustomerService customerService;

  @Autowired OrderService orderService;

  // Customer section
  @PreAuthorize("hasRole('MODERATOR')")
  @GetMapping("customers")
  public ResponseEntity<?> getAll(Authentication authentication) {
    return ResponseEntity.ok().body(customerService.getByUsername(authentication.getName()));
  }

  @PreAuthorize("hasRole('MODERATOR')")
  @PostMapping("customer/add")
  public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
    MessageResponse output = customerService.create(customerRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('MODERATOR')")
  @PutMapping("customer/modify/{id}")
  public ResponseEntity<?> changeCustomer(
      @Valid @PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
    MessageResponse output = customerService.update(id, customerRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('MODERATOR')")
  @DeleteMapping("customer/delete/{id}")
  public ResponseEntity<?> deleteCustomer(@Valid @PathVariable Long id) {
    MessageResponse output = customerService.delete(id);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('MODERATOR')")
  @GetMapping("customer/{id}")
  public ResponseEntity<?> getById(@Valid @PathVariable Long id) {
    return ResponseEntity.ok().body(customerService.getById(id));
  }

  // Order section
  @PreAuthorize("hasRole('MODERATOR')")
  @PostMapping("order/add")
  public ResponseEntity<?> addOrder(@Valid @RequestBody OrderRequest orderRequest) {
    MessageResponse output = orderService.create(orderRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('MODERATOR')")
  @GetMapping("orders")
  public ResponseEntity<?> getAllOrder() {
    return ResponseEntity.ok().body(orderService.getAll());
  }

  @PreAuthorize("hasRole('MODERATOR')")
  @PutMapping("order/modify/{id}")
  public ResponseEntity<?> changeOrder(
      @Valid @PathVariable Long id, @RequestBody OrderRequest orderRequest) {
    MessageResponse output = orderService.update(id, orderRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('MODERATOR')")
  @DeleteMapping("order/delete/{id}")
  public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id) {
    MessageResponse output = orderService.delete(id);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('MODERATOR')")
  @GetMapping("order/{id}")
  public ResponseEntity<?> getOrderById(@Valid @PathVariable Long id) {
    return ResponseEntity.ok().body(orderService.getById(id));
  }
}
