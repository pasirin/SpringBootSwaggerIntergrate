package com.example.demo.services;

import com.example.demo.payload.response.MessageResponse;

import java.util.Collection;
import java.util.Optional;

public interface Service<T, B> {
  MessageResponse create(T object);

  MessageResponse update(Long id, T object);

  MessageResponse delete(Long id);

  Collection<B> getAll();

  Optional<B> getById(Long id);
}
