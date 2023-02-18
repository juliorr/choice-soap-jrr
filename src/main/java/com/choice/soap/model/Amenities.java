package com.choice.soap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Amenities {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id = 0L;

  private String name = "";


  protected Amenities() {
  }

  public Amenities(String name) {
    this.name = name;
  }


  @Override
  public String toString() {
    return String.format(
        "Hotel[id=%d, name='%s']",
        id, name);
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
}
