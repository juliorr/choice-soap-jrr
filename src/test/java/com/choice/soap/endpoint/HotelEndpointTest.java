package com.choice.soap.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;

import com.choice.soap.model.Amenities;
import com.choice.soap.model.Hotel;
import com.choice.soap.service.HotelServiceInterface;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import com.choice.soap.gen.CreateHotelRequest;
import com.choice.soap.gen.CreateHotelResponse;
import com.choice.soap.gen.DeleteHotelRequest;
import com.choice.soap.gen.DeleteHotelResponse;
import com.choice.soap.gen.GetHotelRequest;
import com.choice.soap.gen.GetHotelResponse;
import com.choice.soap.gen.GetListRequest;
import com.choice.soap.gen.GetListResponse;
import com.choice.soap.gen.SearchByNameRequest;
import com.choice.soap.gen.SearchByNameResponse;
import com.choice.soap.gen.UpdateHotelRequest;
import com.choice.soap.gen.UpdateHotelResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

class HotelEndpointTest {

  private HotelServiceInterface hotelServiceInterface;


  private HotelEndpoint endpoint;
  private GetHotelResponse getHotelResponse;
  private GetHotelRequest getHotelRequest;
  private UpdateHotelResponse updateHotelResponse;
  private UpdateHotelRequest updateHotelRequest;
  private CreateHotelResponse createHotelResponse;
  private CreateHotelRequest createHotelRequest;
  private DeleteHotelRequest deleteHotelRequest;
  private DeleteHotelResponse deleteHotelResponse;
  private GetListRequest getListRequest;
  private GetListResponse getListResponse;
  private SearchByNameRequest searchByNameRequest;
  private SearchByNameResponse searchByNameResponse;
  private Hotel hotelModel;
  Pageable pageable;
  Page<com.choice.soap.gen.Hotel> hotelPage;

  @BeforeEach
  void setUp() {
    getHotelRequest = new GetHotelRequest();
    getHotelResponse = new GetHotelResponse();
    updateHotelResponse = new UpdateHotelResponse();
    updateHotelRequest = new UpdateHotelRequest();
    createHotelRequest = new CreateHotelRequest();
    createHotelResponse = new CreateHotelResponse();
    deleteHotelRequest = new DeleteHotelRequest();
    deleteHotelResponse = new DeleteHotelResponse();
    getListRequest = new GetListRequest();
    getListRequest.setPage(0);
    getListRequest.setSize(1);
    getListResponse = new GetListResponse();
    searchByNameRequest = new SearchByNameRequest();
    searchByNameRequest.setPage(0);
    searchByNameRequest.setSize(1);
    searchByNameResponse = new SearchByNameResponse();
    hotelServiceInterface = mock(HotelServiceInterface.class);
    pageable = mock(Pageable.class);

    List<com.choice.soap.gen.Hotel> listHotelEntity = new java.util.ArrayList<>(Collections.emptyList());
    hotelPage = new PageImpl<>(listHotelEntity, pageable, 1);


    hotelModel = new Hotel("some name", "some address", 1, new HashSet<Amenities>());
    com.choice.soap.gen.Hotel hotelDomain = new com.choice.soap.gen.Hotel();
    Mockito.when(hotelServiceInterface.getById(anyInt()))
        .thenReturn(hotelDomain);
    Mockito.when(hotelServiceInterface.update(any(com.choice.soap.gen.Hotel.class)))
        .thenReturn(hotelDomain);
    Mockito.when(hotelServiceInterface.create(any(CreateHotelRequest.class)))
        .thenReturn(hotelDomain);
    Mockito.when(hotelServiceInterface.delete(anyInt()))
        .thenReturn("Success");

    Mockito.when(hotelServiceInterface.findAll(any(Pageable.class)))
        .thenReturn(hotelPage);
    Mockito.when(hotelServiceInterface.findByName(any(), any(Pageable.class)))
        .thenReturn(hotelPage);
    endpoint = new HotelEndpoint(hotelServiceInterface);

  }

  @AfterEach
  void tearDown() {
    hotelModel = null;
    hotelServiceInterface = null;
    endpoint = null;
    getHotelResponse = null;
    getHotelRequest = null;
    updateHotelResponse = null;
    updateHotelRequest = null;
    createHotelResponse = null;
    createHotelRequest = null;
  }

  @Test
  void getHotel() {
    getHotelResponse = endpoint.getHotel(getHotelRequest);
    assertInstanceOf(com.choice.soap.gen.Hotel.class, getHotelResponse.getHotel());
  }

  @Test
  void updateHotel() {
    updateHotelRequest.setHotel(new com.choice.soap.gen.Hotel());
    updateHotelResponse = endpoint.updateHotel(updateHotelRequest);
    assertInstanceOf(com.choice.soap.gen.Hotel.class, updateHotelResponse.getHotel());
  }

  @Test
  void createHotel() {
    createHotelResponse = endpoint.addHotel(createHotelRequest);
    assertInstanceOf(com.choice.soap.gen.Hotel.class, createHotelResponse.getHotel());
  }

  @Test
  void deleteHotel() {
    deleteHotelResponse = endpoint.deleteHotel(deleteHotelRequest);
    assertEquals("Success", deleteHotelResponse.getMessage());
  }

  @Test
  void getAllHotels() {
    getListResponse = endpoint.getAllHotels(getListRequest);
    assertInstanceOf(List.class, getListResponse.getHotels());
  }

  @Test
  void searchByName() {
    searchByNameResponse = endpoint.searchByName(searchByNameRequest);
    assertInstanceOf(List.class, searchByNameResponse.getHotels());
  }
}