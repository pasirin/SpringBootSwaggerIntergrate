package com.example.demo.services;

import com.example.demo.payload.response.MessageResponse;

import java.util.Collection;
import java.util.Optional;

public interface Service<T, B> {
  public abstract MessageResponse create(T object);

  public abstract MessageResponse update(Long id, T object);

  public abstract MessageResponse delete(Long id);

  public abstract Collection<B> getAll();

  public abstract Optional<B> getById(Long id);
}
