package com.choice.soap.respository;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import localhost._8081.Hotel;
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


