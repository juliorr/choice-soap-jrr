package com.choice.soap.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

import com.choice.soap.exeptions.NoSuchElementFoundException;
import com.choice.soap.model.Amenities;
import com.choice.soap.model.Hotel;
import com.choice.soap.respository.AmenityRepository;
import com.choice.soap.respository.HotelRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import localhost._8081.Amenity;
import localhost._8081.CreateHotelRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

class HotelServiceTest {

  private HotelService hotelService;
  private HotelRepository hotelRepository;
  private AmenityRepository amenityRepository;
  private Hotel hotelModel;

  @BeforeEach
  void setUp() {
    hotelModel = new Hotel("some_name", "some_addres", 1, new HashSet<Amenities>());
    hotelRepository = mock(HotelRepository.class);
    amenityRepository = mock(AmenityRepository.class);
    hotelService = new HotelService(hotelRepository, amenityRepository);
  }

  @AfterEach
  void tearDown() {
    hotelRepository = null;
    amenityRepository = null;
    hotelService = null;
    hotelModel = null;
  }

  @Test
  void getByIdExpectedException() {
    Mockito.when(hotelRepository.existsById(anyLong())).thenReturn(false);
    NoSuchElementFoundException thrown = Assertions.assertThrows(NoSuchElementFoundException.class,
        () -> {
          hotelService.getById(9999);
        });
  }

  @Test
  void getById() {
    Mockito.when(hotelRepository.existsById(anyLong())).thenReturn(true);
    Mockito.when(hotelRepository.findById(anyLong())).thenReturn(Optional.ofNullable(hotelModel));
    localhost._8081.Hotel response = hotelService.getById(1);
    assertEquals(hotelModel.getName(), response.getName());
  }

  @Test
  void updateExpectedExceptionWhenHotelNotExist() {
    Mockito.when(hotelRepository.existsById(anyLong())).thenReturn(false);
    NoSuchElementFoundException thrown = Assertions.assertThrows(NoSuchElementFoundException.class,
        () -> {
          localhost._8081.Hotel hotelDomain = new localhost._8081.Hotel();
          hotelService.update(hotelDomain);
        });
  }

  @Test
  void updateExpectedExceptionWhenOneAmenityNotExist() {
    Mockito.when(hotelRepository.existsById(anyLong())).thenReturn(true);
    List<Amenity> amenityList = new ArrayList<>();
    amenityList.add(new Amenity());

    Optional<Amenities> optionalAmenities = Optional.empty();
    Mockito.when(amenityRepository.findById(anyLong())).thenReturn(optionalAmenities);

    NoSuchElementFoundException thrown = Assertions.assertThrows(NoSuchElementFoundException.class,
        () -> {
          localhost._8081.Hotel hotelDomain = new localhost._8081.Hotel();
          hotelDomain.getAmenities().addAll(amenityList);
          hotelService.update(hotelDomain);
        });
  }

  @Test
  void update() {
    Mockito.when(hotelRepository.existsById(anyLong())).thenReturn(true);
    List<Amenity> amenityList = new ArrayList<>();
    amenityList.add(new Amenity());

    Optional<Amenities> optionalAmenities = Optional.of(new Amenities("some name"));
    Mockito.when(amenityRepository.findById(anyLong())).thenReturn(optionalAmenities);
    Mockito.when(hotelRepository.save(any(Hotel.class))).thenReturn(hotelModel);

    localhost._8081.Hotel hotelDomain = new localhost._8081.Hotel();
    hotelDomain.getAmenities().addAll(amenityList);
    localhost._8081.Hotel response = hotelService.update(hotelDomain);
    assertEquals("some_name", response.getName());

  }

  @Test
  void create() {
    CreateHotelRequest createHotelRequest = new CreateHotelRequest();
    Mockito.when(hotelRepository.save(any(Hotel.class))).thenReturn(hotelModel);
    localhost._8081.Hotel reponse = hotelService.create(createHotelRequest);
    assertEquals("some_name", reponse.getName());
  }

  @Test
  void deleteExpectedException() {
    Mockito.when(hotelRepository.existsById(anyLong())).thenReturn(false);
    NoSuchElementFoundException thrown = Assertions.assertThrows(NoSuchElementFoundException.class,
        () -> {
          hotelService.delete(9999);
        });
  }

  @Test
  void delete() {
    Mockito.when(hotelRepository.existsById(anyLong())).thenReturn(true);
    String response = hotelService.delete(9999);
    assertEquals("Success", response);
  }

  @Test
  void findAll() {
    Pageable pageable = mock(Pageable.class);
    List<Hotel> listHotelDomain = new java.util.ArrayList<>(Collections.emptyList());
    Page<Hotel> pageHotelDomain = new PageImpl<>(
        listHotelDomain,
        pageable,
        1);
    Mockito.when(hotelRepository.findAll(any(Pageable.class))).thenReturn(pageHotelDomain);
    Page<localhost._8081.Hotel> response = hotelService.findAll(pageable);
    assertEquals(1, response.getTotalElements());
  }

  @Test
  void findByName() {
    Pageable pageable = mock(Pageable.class);
    List<Hotel> listHotelDomain = new java.util.ArrayList<>(Collections.emptyList());
    Set<Amenities> amenitiesSet = new HashSet<>();
    amenitiesSet.add(new Amenities("some_amenity_name"));
    listHotelDomain.add(new Hotel("some_name", "some_address",1, amenitiesSet));
    Page<Hotel> pageHotelDomain = new PageImpl<>(
        listHotelDomain,
        pageable,
        1);
    Mockito.when(hotelRepository.findAllByNameContains(
          anyString(), any(Pageable.class)
        ))
        .thenReturn(pageHotelDomain);
    Page<localhost._8081.Hotel> response = hotelService.findByName("some_search", pageable);
    assertEquals(1, response.getTotalElements());
  }
}