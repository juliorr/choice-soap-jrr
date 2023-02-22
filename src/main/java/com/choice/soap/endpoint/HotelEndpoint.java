package com.choice.soap.endpoint;

import com.choice.soap.service.HotelServiceInterface;
import com.choice.soap.gen.CreateHotelRequest;
import com.choice.soap.gen.CreateHotelResponse;
import com.choice.soap.gen.DeleteHotelRequest;
import com.choice.soap.gen.DeleteHotelResponse;
import com.choice.soap.gen.GetHotelRequest;
import com.choice.soap.gen.GetHotelResponse;
import com.choice.soap.gen.GetListRequest;
import com.choice.soap.gen.GetListResponse;
import com.choice.soap.gen.Hotel;
import com.choice.soap.gen.SearchByNameRequest;
import com.choice.soap.gen.SearchByNameResponse;
import com.choice.soap.gen.UpdateHotelRequest;
import com.choice.soap.gen.UpdateHotelResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class HotelEndpoint {

  private static final String NAMESPACE_URI = "http://localhost:8081";

  private final HotelServiceInterface hotelsService;

  public HotelEndpoint(HotelServiceInterface hotelsService) {
    this.hotelsService = hotelsService;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getHotelRequest")
  @ResponsePayload
  public GetHotelResponse getHotel(@RequestPayload GetHotelRequest request) {
    GetHotelResponse response = new GetHotelResponse();
    response.setHotel(hotelsService.getById(request.getId()));

    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createHotelRequest")
  @ResponsePayload
  public CreateHotelResponse addHotel(@RequestPayload CreateHotelRequest request) {
    CreateHotelResponse response = new CreateHotelResponse();
    response.setHotel(hotelsService.create(request));

    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateHotelRequest")
  @ResponsePayload
  public UpdateHotelResponse updateHotel(@RequestPayload UpdateHotelRequest request) {
    UpdateHotelResponse response = new UpdateHotelResponse();
    response.setHotel(hotelsService.update(request.getHotel()));

    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteHotelRequest")
  @ResponsePayload
  public DeleteHotelResponse deleteHotel(@RequestPayload DeleteHotelRequest request) {
    DeleteHotelResponse response = new DeleteHotelResponse();
    response.setMessage(hotelsService.delete(request.getId()));

    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getListRequest")
  @ResponsePayload
  public GetListResponse getAllHotels(@RequestPayload GetListRequest request) {
    GetListResponse response = new GetListResponse();
    Page<Hotel> hotelPage = hotelsService.findAll(
        PageRequest.of(request.getPage(), request.getSize()));
    response.setPage(hotelPage.getPageable().getPageNumber());
    response.setSize(hotelPage.getPageable().getPageSize());
    response.setTotalElements((int) hotelPage.getTotalElements());
    response.setTotalPages(hotelPage.getTotalPages());
    response.getHotels().addAll(hotelPage.getContent());
    return response;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "searchByNameRequest")
  @ResponsePayload
  public SearchByNameResponse searchByName(@RequestPayload SearchByNameRequest request) {
    SearchByNameResponse response = new SearchByNameResponse();
    Page<Hotel> hotelPage = hotelsService.findByName(
        request.getName(),
        PageRequest.of(request.getPage(), request.getSize())
    );
    response.setPage(hotelPage.getPageable().getPageNumber());
    response.setSize(hotelPage.getPageable().getPageSize());
    response.setTotalElements((int) hotelPage.getTotalElements());
    response.setTotalPages(hotelPage.getTotalPages());
    response.getHotels().addAll(hotelPage.getContent());
    return response;
  }
}
