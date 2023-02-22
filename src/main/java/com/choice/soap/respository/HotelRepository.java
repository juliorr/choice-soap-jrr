package com.choice.soap.respository;

import com.choice.soap.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {
  Page<Hotel> findAllByNameContains(String name, Pageable pageable);
}


