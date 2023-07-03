package com.example.demo.repository;

import com.example.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Boolean existsByName(String name);

  List<Product> findByNameLike(String name);

  Optional<Product> findByCategory(String category);

  Long deleteByName(String name);



  @NotNull
  List<Product> findAll();
}
