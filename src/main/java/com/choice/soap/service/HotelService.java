package com.choice.soap.service;

import com.choice.soap.exeptions.NoSuchElementFoundException;
import com.choice.soap.model.Amenities;
import com.choice.soap.respository.HotelRepository;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import localhost._8081.Amenity;
import localhost._8081.CreateHotelRequest;
import localhost._8081.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HotelService implements HotelServiceInterface {

  private final HotelRepository hotelRepository;

  public HotelService(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  @Override
  @Transactional
  public Hotel getById(int id) {
    Long ID = (long) id;
    if (!this.exists(ID)) {
      throw new NoSuchElementFoundException("Item  does not exist with id =" + ID);
    }
    com.choice.soap.model.Hotel hotelEntity = hotelRepository.findById(ID).get();
    return hotelEntity.convertToDomain();
  }

  @Override
  @Transactional
  public Hotel update(Hotel hotelEntity) throws NoSuchElementFoundException {
    com.choice.soap.model.Hotel hotel = new com.choice.soap.model.Hotel(
        hotelEntity.getName(),
        hotelEntity.getAddress(),
        hotelEntity.getRating(),
        new HashSet<Amenities>()
    );
    hotel.setId((long) hotelEntity.getId());
    if (this.exists(hotel.getId())) {
      return this.save(hotel);
    } else {
      throw new NoSuchElementFoundException("Item  does not exist with id =" + hotel.getId());
    }
  }

  @Override
  @Transactional
  public Hotel create(CreateHotelRequest request) {
    com.choice.soap.model.Hotel hotelModel = new com.choice.soap.model.Hotel(
        request.getName(),
        request.getAddress(),
        request.getRating(),
        new HashSet<Amenities>()
    );
    return this.save(hotelModel);
  }

  @Override
  @Transactional
  public String delete(int id) {
    Long ID = (long) id;
    if (this.exists(ID)) {
      hotelRepository.deleteById(ID);
    } else {
      throw new NoSuchElementFoundException("Item  does not exist with id =" + ID);
    }
    return "Success";
  }

  @Override
  @Transactional
  public Page<Hotel> findAll(Pageable pageable) {
    Page<com.choice.soap.model.Hotel> pageHotelModel = hotelRepository.findAll(pageable);

    return convertFromModelPageToDomainPage(pageHotelModel, pageable);
  }

  @Override
  @Transactional
  public Page<Hotel> findByName(String name, Pageable pageable) {
    Page<com.choice.soap.model.Hotel> pageHotelModel = hotelRepository.findAllByNameContains(name,
        pageable);

    return convertFromModelPageToDomainPage(pageHotelModel, pageable);
  }

  private Page<Hotel> convertFromModelPageToDomainPage(
      Page<com.choice.soap.model.Hotel> pageHotelModel, Pageable pageable) {

    List<Hotel> listHotelEntity = new java.util.ArrayList<>(Collections.emptyList());
    for (com.choice.soap.model.Hotel hotelEntityModel : pageHotelModel.getContent()) {
      Hotel hotel = new Hotel();
      hotelEntityModel.convertToDomain();
      hotel.setId(hotelEntityModel.getId().intValue());
      hotel.setName(hotelEntityModel.getName());
      hotel.setAddress(hotelEntityModel.getAddress());
      hotel.setRating(hotelEntityModel.getRating());

      Set<Amenity> amenityDomain = new HashSet<>(Collections.emptySet());
      for (Amenities amenityModel : hotelEntityModel.getAmenities()) {
        Amenity amenity = new Amenity();
        amenity.setId(amenityModel.getId().intValue());
        amenity.setName(amenityModel.getName());
        amenityDomain.add(amenity);
      }
      hotel.getAmenities().addAll(amenityDomain);

      listHotelEntity.add(hotel);
    }

    return new PageImpl<>(listHotelEntity, pageable, pageHotelModel.getTotalElements());
  }

  @Transactional
  private Hotel save(com.choice.soap.model.Hotel hotelModel) {
    hotelModel = hotelRepository.save(hotelModel);
    return hotelModel.convertToDomain();
  }

  private boolean exists(Long id) {
    return hotelRepository.existsById(id);
  }
}
