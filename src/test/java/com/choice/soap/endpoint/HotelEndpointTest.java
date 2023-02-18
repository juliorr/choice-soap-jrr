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
import localhost._8081.CreateHotelRequest;
import localhost._8081.CreateHotelResponse;
import localhost._8081.DeleteHotelRequest;
import localhost._8081.DeleteHotelResponse;
import localhost._8081.GetHotelRequest;
import localhost._8081.GetHotelResponse;
import localhost._8081.GetListRequest;
import localhost._8081.GetListResponse;
import localhost._8081.SearchByNameRequest;
import localhost._8081.SearchByNameResponse;
import localhost._8081.UpdateHotelRequest;
import localhost._8081.UpdateHotelResponse;
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
  Page<localhost._8081.Hotel> hotelPage;

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

    List<localhost._8081.Hotel> listHotelEntity = new java.util.ArrayList<>(Collections.emptyList());
    hotelPage = new PageImpl<>(listHotelEntity, pageable, 1);


    hotelModel = new Hotel("some name", "some address", 1, new HashSet<Amenities>());
    localhost._8081.Hotel hotelDomain = new localhost._8081.Hotel();
    Mockito.when(hotelServiceInterface.getById(anyInt()))
        .thenReturn(hotelDomain);
    Mockito.when(hotelServiceInterface.update(any(localhost._8081.Hotel.class)))
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
    assertInstanceOf(localhost._8081.Hotel.class, getHotelResponse.getHotel());
  }

  @Test
  void updateHotel() {
    updateHotelRequest.setHotel(new localhost._8081.Hotel());
    updateHotelResponse = endpoint.updateHotel(updateHotelRequest);
    assertInstanceOf(localhost._8081.Hotel.class, updateHotelResponse.getHotel());
  }

  @Test
  void createHotel() {
    createHotelResponse = endpoint.addHotel(createHotelRequest);
    assertInstanceOf(localhost._8081.Hotel.class, createHotelResponse.getHotel());
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