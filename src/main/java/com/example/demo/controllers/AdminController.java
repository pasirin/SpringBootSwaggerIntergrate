package com.example.demo.controllers;

import com.example.demo.payload.request.CustomerRequest;
import com.example.demo.payload.request.OrderRequest;
import com.example.demo.payload.request.ProductRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.services.CustomerService;
import com.example.demo.services.OrderService;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class AdminController {
  @Autowired CustomerService customerService;

  @Autowired OrderService orderService;

  @Autowired ProductService productService;

  // Customer Section
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("customers")
  public ResponseEntity<?> getAllCustomer() {
    return ResponseEntity.ok().body(customerService.getAll());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("customer/add")
  public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
    MessageResponse output = customerService.create(customerRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("customer/modify/{id}")
  public ResponseEntity<?> changeCustomer(
      @Valid @PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
    MessageResponse output = customerService.update(id, customerRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("customer/delete/{id}")
  public ResponseEntity<?> deleteCustomer(@Valid @PathVariable Long id) {
    MessageResponse output = customerService.delete(id);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("customer/{id}")
  public ResponseEntity<?> getCustomerById(@Valid @PathVariable Long id) {
    return ResponseEntity.ok().body(customerService.getById(id));
  }

  // Order Section
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("order/add")
  public ResponseEntity<?> addOrder(@Valid @RequestBody OrderRequest orderRequest) {
    MessageResponse output = orderService.create(orderRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("orders")
  public ResponseEntity<?> getAllOrder() {
    return ResponseEntity.ok().body(orderService.getAll());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("order/modify/{id}")
  public ResponseEntity<?> changeOrder(
      @Valid @PathVariable Long id, @RequestBody OrderRequest orderRequest) {
    MessageResponse output = orderService.update(id, orderRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("order/delete/{id}")
  public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id) {
    MessageResponse output = orderService.delete(id);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("order/{id}")
  public ResponseEntity<?> getOrderById(@Valid @PathVariable Long id) {
    return ResponseEntity.ok().body(orderService.getById(id));
  }

  // Product Section
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("product/add")
  public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest productRequest) {
    MessageResponse output = productService.create(productRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("product/modify/{id}")
  public ResponseEntity<?> changeProduct(
      @Valid @PathVariable Long id, @RequestBody ProductRequest productRequest) {
    MessageResponse output = productService.update(id, productRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("product/delete/{id}")
  public ResponseEntity<?> deleteProduct(@Valid @PathVariable Long id) {
    MessageResponse output = productService.delete(id);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }
}
