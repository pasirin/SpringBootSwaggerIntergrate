package com.example.demo.controllers;

import com.example.demo.payload.request.CustomerRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
  @Autowired CustomerService customerService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok().body(customerService.getAll());
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @PostMapping("/add")
  public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
    MessageResponse output = customerService.create(customerRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @PutMapping("/modify/{id}")
  public ResponseEntity<?> changeCustomer(
      @Valid @PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
    MessageResponse output = customerService.update(id, customerRequest);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteCustomer(@Valid @PathVariable Long id) {
    MessageResponse output = customerService.delete(id);
    return output.getStatus()
        ? ResponseEntity.ok().build()
        : ResponseEntity.badRequest().body(output.getMessage());
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@Valid @PathVariable Long id) {
    return ResponseEntity.ok().body(customerService.getById(id));
  }
}
