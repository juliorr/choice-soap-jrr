package com.choice.soap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Hotel {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  private String name;
  private String address;
  private Integer rating;


  protected Hotel() {}

  public Hotel(String name, String address, Integer rating) {
    this.name = name;
    this.address = address;
    this.rating = rating;
  }

  @Override
  public String toString() {
    return String.format(
        "Hotel[id=%d, name='%s', address='%s',rating=%d]",
        id, name, address, rating);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public Integer getRating() {
    return rating;
  }

  public localhost._8081.Hotel convertToDomain() {
    localhost._8081.Hotel hotelDomain = new localhost._8081.Hotel();
    hotelDomain.setId(this.getId().intValue());
    hotelDomain.setAddress(this.getAddress());
    hotelDomain.setName(this.getName());
    hotelDomain.setRating(this.getRating());
    return hotelDomain;
  }
}
