package com.choice.soap.endpoint;

import com.choice.soap.exeptions.NoSuchElementFoundException;
import com.choice.soap.service.HotelServiceInterface;
import localhost._8081.CreateHotelRequest;
import localhost._8081.CreateHotelResponse;
import localhost._8081.DeleteHotelRequest;
import localhost._8081.DeleteHotelResponse;
import localhost._8081.GetHotelRequest;
import localhost._8081.GetHotelResponse;
import localhost._8081.GetListRequest;
import localhost._8081.GetListResponse;
import localhost._8081.Hotel;
import localhost._8081.UpdateHotelRequest;
import localhost._8081.UpdateHotelResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    Page<Hotel> hotelPage = hotelsService.findAll(PageRequest.of(request.getPage(), request.getSize()));
    response.setPage(hotelPage.getPageable().getPageNumber());
    response.setSize(hotelPage.getPageable().getPageSize());
    response.setTotalElements((int) hotelPage.getTotalElements());
    response.setTotalPages(hotelPage.getTotalPages());
    response.getHotels().addAll(hotelPage.getContent());
    return response;
  }
}
