package com.choice.soap.respository;

import com.choice.soap.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
  Hotel findByName(String productName);
}


