package com.choice.soap.respository;

import io.spring.guides.gs_producing_web_service.Hotel;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class HotelRepository {
  private static final Map<String, Hotel> hotels = new HashMap<>();

  @PostConstruct
  public void initData() {
  }

  public Hotel findHotel(String name) {
    return hotels.get(name);
  }
}


