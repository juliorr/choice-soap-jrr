package com.choice.soap.service;

import com.choice.soap.exeptions.NoSuchElementFoundException;
import localhost._8081.CreateHotelRequest;
import localhost._8081.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotelServiceInterface {

  Hotel getById(int id);

  Hotel update(Hotel hotelDomain) throws NoSuchElementFoundException;

  Hotel create(CreateHotelRequest request);

  String delete(int id) throws NoSuchElementFoundException;

  Page<Hotel> findAll(Pageable pageable);

  Page<Hotel> findByName(String name, Pageable pageable);
}
