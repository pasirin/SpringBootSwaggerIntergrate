package com.example.demo.repository;

import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Boolean existsByUsername(String username);

  Long deleteByUsername(String username);

  Optional<User> findByUsername(String username);

  @NotNull
  List<User> findAll();
}
