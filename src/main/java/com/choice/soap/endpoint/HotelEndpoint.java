package com.choice.soap.endpoint;

import com.choice.soap.model.Hotel;
import com.choice.soap.respository.HotelRepository;
import localhost._8081.CreateHotelRequest;
import localhost._8081.CreateHotelResponse;
import localhost._8081.GetHotelRequest;
import localhost._8081.GetHotelResponse;
import localhost._8081.UpdateHotelRequest;
import localhost._8081.UpdateHotelResponse;
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

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createHotelRequest")
  @ResponsePayload
  public CreateHotelResponse addHotel(@RequestPayload CreateHotelRequest request) {
    CreateHotelResponse response = new CreateHotelResponse();

    Hotel hotelEntity = hotelRepository.save(new Hotel(
        request.getName(),
        request.getAddress(),
        request.getRating()
    ));
    response.setHotel(hotelEntity.convertToDomain());

    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateHotelRequest")
  @ResponsePayload
  public UpdateHotelResponse updateHotel(@RequestPayload UpdateHotelRequest request) {
    UpdateHotelResponse response = new UpdateHotelResponse();

    Hotel hotel = new Hotel(
        request.getHotel().getName(),
        request.getHotel().getAddress(),
        request.getHotel().getRating());
    hotel.setId((long) request.getHotel().getId());

    Hotel hotelEntity = hotelRepository.save(hotel);
    response.setHotel(hotelEntity.convertToDomain());

    return response;
  }
}
