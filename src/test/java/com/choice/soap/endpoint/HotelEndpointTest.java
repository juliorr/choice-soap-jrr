package com.choice.soap.endpoint;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import com.choice.soap.model.Hotel;
import com.choice.soap.respository.HotelRepository;
import localhost._8081.GetHotelRequest;
import localhost._8081.GetHotelResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class HotelEndpointTest {

  private HotelRepository hotelRepository;
  private HotelEndpoint endpoint;
  private GetHotelResponse getHotelResponse;
  private GetHotelRequest getHotelRequest;

  private Hotel hotelModel;

  @BeforeEach
  void setUp() {
    hotelRepository = mock(HotelRepository.class);
    hotelModel = new Hotel("some name", "some address", 1);
    Mockito.when(hotelRepository.findByName(any()))
        .thenReturn(hotelModel);
    endpoint = new HotelEndpoint(hotelRepository);
    getHotelRequest = new GetHotelRequest();
    getHotelResponse = new GetHotelResponse();
  }

  @AfterEach
  void tearDown() {
    hotelModel = null;
    hotelRepository = null;
    endpoint = null;
    getHotelResponse = null;
    getHotelRequest = null;
  }

  @Test
  void getHotel() {
    getHotelResponse = endpoint.getHotel(getHotelRequest);
    assertInstanceOf(localhost._8081.Hotel.class, getHotelResponse.getHotel());
  }
}