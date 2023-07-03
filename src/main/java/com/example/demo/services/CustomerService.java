package com.example.demo.services;

import com.example.demo.models.Customer;
import com.example.demo.models.User;
import com.example.demo.payload.request.CustomerRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.util.Collection;
import java.util.Optional;

@org.springframework.stereotype.Service
public class CustomerService implements Service<CustomerRequest, Customer> {
  @Autowired CustomerRepository customerRepository;

  @Autowired UserRepository userRepository;

  @Override
  public Collection<Customer> getAll() {
    return customerRepository.findAll();
  }

  @Override
  public MessageResponse create(CustomerRequest customerRequest) {
    if (!userRepository.existsById(customerRequest.getUserId())) {
      return MessageResponse.body("The Specified User does not exist");
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
    return MessageResponse.status(
        true); // .setMessage("Successfully added the requested customer");
  }

  @Override
  public MessageResponse update(Long id, CustomerRequest customerRequest) {
    if (!customerRepository.existsById(id)) {
      return MessageResponse.body("There Are No Customer with the id: " + id);
    } else if (!userRepository.existsById(customerRequest.getUserId())) {
      return MessageResponse.body("The Specified User does not exist");
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
      return MessageResponse.status(true);
      // .setMessage("The Customer with the id: " + id + " is updated");
    }
  }

  @Override
  public MessageResponse delete(Long id) {
    if (!customerRepository.existsById(id)) {
      return MessageResponse.body("There Are No Customer with the id: " + id);
    } else {
      customerRepository.deleteById(id);
      return MessageResponse.status(true);
      // .setMessage("The Customer With the id: " + id + " is deleted");
    }
  }

  @Override
  public Optional<Customer> getById(Long id) {
    if (!customerRepository.existsById(id)) {
      return Optional.empty();
    } else {
      return customerRepository.findById(id);
    }
  }

  public Long getUserIdFromUsername(String name) {
    Optional<User> user = userRepository.findByUsername(name);
    return user.isEmpty() ? null : user.get().getId();
  }

  public Optional<Customer> getByUsername(String username) {
    Long id = getUserIdFromUsername(username);
    if (id == null) {
      return Optional.empty();
    } else {
      return customerRepository.findByUserId(id);
    }
  }
}
