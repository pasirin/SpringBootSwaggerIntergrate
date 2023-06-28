package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull private Date order_date;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "productId")
  private Product product;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerId")
  private Customer customer;

  public Order() {}

  public Order(Date order_date) {
    this.order_date = order_date;
  }

  public Long getId() {
    return id;
  }

  public Date getOrder_date() {
    return order_date;
  }

  public Customer getCustomer() {
    return customer;
  }

  public Product getProduct() {
    return product;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setOrder_date(Date order_date) {
    this.order_date = order_date;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
