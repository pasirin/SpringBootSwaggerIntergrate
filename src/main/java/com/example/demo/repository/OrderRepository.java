package com.example.demo.repository;

import com.example.demo.models.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
  @NotNull
  List<Order> findAll();

  Boolean existsByCustomer(Long customerId);

  Optional<Order> findByCustomerId(Long customerId);

  void deleteByCustomerIdAndId(Long customerId, Long id);

  Optional<Order> findByCustomerIdAndId(Long customerId, Long id);
}
