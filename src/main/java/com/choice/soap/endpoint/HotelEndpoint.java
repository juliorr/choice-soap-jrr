package com.choice.soap.endpoint;

import com.choice.soap.model.Hotel;
import com.choice.soap.respository.HotelRepository;
import localhost._8081.GetHotelRequest;
import localhost._8081.GetHotelResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HotelEndpoint {

  private static final String NAMESPACE_URI = "http://localhost:8081";

  private final HotelRepository hotelRepository;

  public HotelEndpoint(HotelRepository hotelRepository) {
    this.hotelRepository = hotelRepository;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getHotelRequest")
  @ResponsePayload
  public GetHotelResponse getHotel(@RequestPayload GetHotelRequest request) {
    GetHotelResponse response = new GetHotelResponse();
    Hotel hotelEntity = hotelRepository.findByName(request.getName());
    response.setHotel(hotelEntity.convertToDomain());

    return response;
  }
}
