package com.example.demo.services;

import com.example.demo.models.Order;
import com.example.demo.payload.request.OrderRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service
public class OrderService implements Service<OrderRequest, Order> {
  @Autowired OrderRepository orderRepository;

  @Autowired ProductRepository productRepository;

  @Autowired CustomerRepository customerRepository;

  @Override
  public MessageResponse create(OrderRequest orderRequest) {
    if (!productRepository.existsById(orderRequest.getProductId())) {
      return MessageResponse.body("The Specified Product does not exist");
    }
    if (!customerRepository.existsById(orderRequest.getCustomerId())) {
      return MessageResponse.body("The Specified Customer does not exist");
    } else {
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
      return MessageResponse.status(true); // .setMessage("Successfully added the requested order");
    }
  }

  @Override
  public MessageResponse update(Long id, OrderRequest orderRequest) {
    if (!orderRepository.existsById(id)) {
      return MessageResponse.body("There Are No Order with the id: " + id);
    } else if (!productRepository.existsById(orderRequest.getProductId())) {
      return MessageResponse.body("The Specified Product does not exist");
    } else if (!customerRepository.existsById(orderRequest.getCustomerId())) {
      return MessageResponse.body("The Specified Customer does not exist");
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
      return MessageResponse.status(true);
      // .setMessage("The Order with the id: " + id + " is updated");
    }
  }

  @Override
  public MessageResponse delete(Long id) {
    if (!orderRepository.existsById(id)) {
      return MessageResponse.body("There Are No Order with the id: " + id);
    } else {
      orderRepository.deleteById(id);
      return MessageResponse.status(true);
      // .setMessage("The Order With the id: " + id + " is deleted");
    }
  }

  @Override
  public Collection<Order> getAll() {
    return orderRepository.findAll();
  }

  @Override
  public Optional<Order> getById(Long id) {
    if (!orderRepository.existsById(id)) {
      return Optional.empty();
    } else {
      return orderRepository.findById(id);
    }
  }
}
