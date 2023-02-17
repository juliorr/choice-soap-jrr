package com.choice.soap.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import localhost._8081.Amenity;

@Entity
public class Hotel {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id = 0L;

  private String name = "";
  private String address = "";
  private Integer rating = 0;
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Amenities> amenities;


  protected Hotel() {
  }

  public Hotel(String name, String address, Integer rating, Set<Amenities> amenities) {
    this.name = name;
    this.address = address;
    this.rating = rating;
    this.amenities = amenities;
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

  public void setId(Long id) {
    this.id = id;
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

  public Set<Amenities> getAmenities() {
    return amenities;
  }

  public void setAmenities(Set<Amenities> amenities) {
    this.amenities = amenities;
  }

  public localhost._8081.Hotel convertToDomain() {
    localhost._8081.Hotel hotelDomain = new localhost._8081.Hotel();
    hotelDomain.setId(this.getId().intValue());
    hotelDomain.setAddress(this.getAddress());
    hotelDomain.setName(this.getName());
    hotelDomain.setRating(this.getRating());
    Set<Amenity> amenityDomain = new HashSet<>(Collections.emptySet());
    for (Amenities amenityModel : this.getAmenities()) {
      Amenity amenity = new Amenity();
      amenity.setId(amenityModel.getId().intValue());
      amenity.setName(amenityModel.getName());
      amenityDomain.add(amenity);
    }
    hotelDomain.getAmenities().addAll(amenityDomain);

    return hotelDomain;
  }
}
