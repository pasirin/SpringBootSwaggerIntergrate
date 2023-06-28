package com.example.demo.repository;

import com.example.demo.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  @NotNull
  List<Order> findAll();
}
