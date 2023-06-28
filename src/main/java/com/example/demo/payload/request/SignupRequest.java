package com.example.demo.payload.request;

import javax.validation.constraints.NotBlank;

public class SignupRequest {
  @NotBlank private String username;

  @NotBlank private String password;

  @NotBlank private String role;

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
