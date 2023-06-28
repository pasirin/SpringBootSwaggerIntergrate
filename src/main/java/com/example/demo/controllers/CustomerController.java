package com.example.demo.controllers;

import com.example.demo.models.Customer;
import com.example.demo.payload.request.CustomerRequest;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/customer")
public class CustomerController {
  @Autowired CustomerRepository customerRepository;

  @Autowired UserRepository userRepository;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/all")
  public List<Customer> getAll() {
    return customerRepository.findAll();
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @PostMapping("/add")
  public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
    if (!userRepository.existsById(customerRequest.getUserId())) {
      return ResponseEntity.badRequest().body("The Specified User does not exist");
    }
    Customer customer = new Customer(customerRequest.getName());
    customer.setUser(
        userRepository
            .findById(customerRequest.getUserId())
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Server ERROR: could not find user with the id: "
                            + customerRequest.getUserId())));
    customerRepository.save(customer);
    return ResponseEntity.ok().body("Successfully added the requested customer");
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteCustomer(@Valid @PathVariable Long id) {
    if (!customerRepository.existsById(id)) {
      return ResponseEntity.badRequest().body("There Are No Customer with the id: " + id);
    } else {
      customerRepository.deleteById(id);
      return ResponseEntity.ok().body("The Customer With the id: " + id + " is deleted");
    }
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @PutMapping("/modify/{id}")
  public ResponseEntity<?> changeCustomer(
      @Valid @PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
    if (!customerRepository.existsById(id)) {
      return ResponseEntity.badRequest().body("There Are No Customer with the id: " + id);
    } else if (!userRepository.existsById(customerRequest.getUserId())) {
      return ResponseEntity.badRequest().body("The Specified User does not exist");
    } else {
      Customer customer =
          customerRepository
              .findById(id)
              .orElseThrow(
                  () ->
                      new RuntimeException(
                          "Server ERROR: could not find customer with the id: " + id));
      customer.setName(customerRequest.getName());
      customer.setUser(
          userRepository
              .findById(customerRequest.getUserId())
              .orElseThrow(
                  () ->
                      new RuntimeException(
                          "Server ERROR: could not find user with id: "
                              + customerRequest.getUserId())));
      customerRepository.save(customer);
      return ResponseEntity.ok().body("The Customer with the id: " + id + " is updated");
    }
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@Valid @PathVariable Long id) {
    if (!customerRepository.existsById(id)) {
      return ResponseEntity.badRequest().body("There Are No Customer with the id: " + id);
    } else {
      return ResponseEntity.ok().body(customerRepository.findById(id));
    }
  }
}
