package com.example.demo.payload.response;

public class MessageResponse {
  private boolean success = false;
  private String message;

  public MessageResponse() {}

  public MessageResponse(boolean success) {
    this.success = success;
  }

  public MessageResponse(String message) {
    this.message = message;
  }

  public MessageResponse setSuccess(boolean success) {
    this.success = success;
    return this;
  }

  public MessageResponse setMessage(String message) {
    this.message = message;
    return this;
  }

  public static MessageResponse status(boolean success) {
    return new MessageResponse(success);
  }

  public static MessageResponse body(String message) {
    return new MessageResponse(message);
  }

  public String getMessage() {
    return message;
  }

  public boolean getStatus() {
    return success;
  }

  public MessageResponse build() {
    return this;
  }
}
