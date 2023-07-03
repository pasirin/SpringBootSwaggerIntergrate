package com.example.demo.repository;

import com.example.demo.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findByName(String name);

  Optional<Customer> findByUserId(Long UserId);

  @NotNull
  List<Customer> findAll();
}
