package com.example.demo.controllers;

import com.example.demo.models.Order;
import com.example.demo.payload.request.OrderRequest;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {
  @Autowired OrderRepository orderRepository;

  @Autowired ProductRepository productRepository;

  @Autowired CustomerRepository customerRepository;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/all")
  public List<Order> getAll() {
    return orderRepository.findAll();
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @PostMapping("/add")
  public ResponseEntity<?> addOrder(@Valid @RequestBody OrderRequest orderRequest) {
    if (!productRepository.existsById(orderRequest.getProductId())) {
      return ResponseEntity.badRequest().body("The Specified Product does not exist");
    }
    if (!customerRepository.existsById(orderRequest.getCustomerId())) {
      return ResponseEntity.badRequest().body("The Specified Customer does not exist");
    }

    Order order = new Order(orderRequest.getOrderDate());
    order.setProduct(
        productRepository
            .findById(orderRequest.getProductId())
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Server ERROR: could not find product with the id: "
                            + orderRequest.getProductId())));
    order.setCustomer(
        customerRepository
            .findById(orderRequest.getCustomerId())
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Server ERROR: could not find customer with id: "
                            + orderRequest.getCustomerId())));
    orderRepository.save(order);
    return ResponseEntity.ok().body("Successfully added the requested order");
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @PutMapping("/modify/{id}")
  public ResponseEntity<?> changeOrder(
      @Valid @PathVariable Long id, @RequestBody OrderRequest orderRequest) {
    if (!orderRepository.existsById(id)) {
      return ResponseEntity.badRequest().body("There Are No Order with the id: " + id);
    } else if (!productRepository.existsById(orderRequest.getProductId())) {
      return ResponseEntity.badRequest().body("The Specified Product does not exist");
    } else if (!customerRepository.existsById(orderRequest.getCustomerId())) {
      return ResponseEntity.badRequest().body("The Specified Customer does not exist");
    } else {
      Order order =
          orderRepository
              .findById(id)
              .orElseThrow(
                  () ->
                      new RuntimeException(
                          "Server ERROR: could not find order with the id: " + id));
      order.setOrder_date(orderRequest.getOrderDate());
      order.setProduct(
          productRepository
              .findById(orderRequest.getProductId())
              .orElseThrow(
                  () ->
                      new RuntimeException(
                          "Server ERROR: could not find product with the id: "
                              + orderRequest.getProductId())));
      order.setCustomer(
          customerRepository
              .findById(orderRequest.getCustomerId())
              .orElseThrow(
                  () ->
                      new RuntimeException(
                          "Server ERROR: could not find customer with the id: "
                              + orderRequest.getCustomerId())));
      orderRepository.save(order);
      return ResponseEntity.ok().body("The Order with the id: " + id + " is updated");
    }
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteOrder(@Valid @PathVariable Long id) {
    if (!orderRepository.existsById(id)) {
      return ResponseEntity.badRequest().body("There Are No Order with the id: " + id);
    } else {
      orderRepository.deleteById(id);
      return ResponseEntity.ok().body("The Order With the id: " + id + " is deleted");
    }
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
  @GetMapping("/{id}")
  public ResponseEntity<?> getById(@Valid @PathVariable Long id) {
    if (!orderRepository.existsById(id)) {
      return ResponseEntity.badRequest().body("There Are No Order with the id: " + id);
    } else {
      return ResponseEntity.ok().body(orderRepository.findById(id));
    }
  }
}
