package com.choice.soap.service;

import com.choice.soap.exeptions.NoSuchElementFoundException;
import com.choice.soap.respository.HotelRepository;
import java.util.Collections;
import java.util.List;
import localhost._8081.CreateHotelRequest;
import localhost._8081.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
public class HotelService implements HotelServiceInterface {

  private final HotelRepository hotelRepository;

  public HotelService(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  @Override
  public Hotel getById(int id) {
    Long ID = (long) id;
    if (!this.exists(ID)) {
      throw new NoSuchElementFoundException("Item  does not exist with id =" +  ID);
    }
    com.choice.soap.model.Hotel hotelEntity = hotelRepository.findById(ID).get();
    return hotelEntity.convertToDomain();
  }

  @Override
  public Hotel update(Hotel hotelEntity) throws NoSuchElementFoundException {
    com.choice.soap.model.Hotel hotel = new com.choice.soap.model.Hotel(
        hotelEntity.getName(),
        hotelEntity.getAddress(),
        hotelEntity.getRating());
    hotel.setId((long) hotelEntity.getId());
    if (this.exists(hotel.getId())) {
      return this.save(hotel);
    } else {
      throw new NoSuchElementFoundException("Item  does not exist with id =" +  hotel.getId());
    }
  }

  @Override
  public Hotel create(CreateHotelRequest request) {
    com.choice.soap.model.Hotel hotelModel = new com.choice.soap.model.Hotel(
        request.getName(),
        request.getAddress(),
        request.getRating()
    );
    return this.save(hotelModel);
  }

  @Override
  public String delete(int id) {
    Long ID = (long) id;
    if (this.exists(ID)) {
      hotelRepository.deleteById(ID);
    } else {
      throw new NoSuchElementFoundException("Item  does not exist with id =" +  ID);
    }
    return "Success";
  }

  @Override
  public Page<Hotel> findAll(Pageable pageable) {
    Page<com.choice.soap.model.Hotel> pageHotelModel = hotelRepository.findAll(pageable);

    List<Hotel> listHotelEntity = new java.util.ArrayList<>(Collections.emptyList());
    for (com.choice.soap.model.Hotel hotelEntityModel : pageHotelModel.getContent()) {
      Hotel hotel = new Hotel();
      hotel.setId(hotelEntityModel.getId().intValue());
      hotel.setName(hotelEntityModel.getName());
      hotel.setAddress(hotelEntityModel.getAddress());
      hotel.setRating(hotelEntityModel.getRating());
      listHotelEntity.add(hotel);
    }
    // public PageImpl(List<T> content, Pageable pageable, long total) {

    return new PageImpl<>(listHotelEntity, pageable,pageHotelModel.getTotalElements());
  }

  private Hotel save(com.choice.soap.model.Hotel hotelModel) {
    hotelModel = hotelRepository.save(hotelModel);
    return hotelModel.convertToDomain();
  }

  private boolean exists(Long id) {
    return hotelRepository.existsById(id);
  }
}
