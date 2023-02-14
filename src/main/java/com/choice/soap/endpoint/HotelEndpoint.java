package com.choice.soap.endpoint;

import com.choice.soap.respository.HotelRepository;
import io.spring.guides.gs_producing_web_service.GetHotelRequest;
import io.spring.guides.gs_producing_web_service.GetHotelResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HotelEndpoint {

  private static final String NAMESPACE_URI = "http://localhost:8081/";

  private HotelRepository hotelRepository;

  @Autowired
  public HotelEndpoint(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getHotelRequest")
  @ResponsePayload
  public GetHotelResponse getHotel(@RequestPayload GetHotelRequest request) {
    GetHotelResponse response = new GetHotelResponse();
    response.setHotel(hotelRepository.findHotel(request.getName()));

    return response;
  }
}
