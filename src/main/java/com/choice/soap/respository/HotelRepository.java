package com.choice.soap.respository;

import com.choice.soap.model.Hotel;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {
  Hotel findByName(String productName);
}


