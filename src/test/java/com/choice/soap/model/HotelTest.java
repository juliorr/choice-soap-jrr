package com.choice.soap.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HotelTest {

  public static Stream<Arguments> providerConvertToDomain() {
    return Stream.of(
        Arguments.of(new Hotel()),
        Arguments.of(new Hotel("name", "address", 1))
    );
  }

  @ParameterizedTest
  @MethodSource("providerConvertToDomain")
  void testConvertToDomain(Hotel hotel) {
    localhost._8081.Hotel hotelDomain = hotel.convertToDomain();
    assertEquals(hotel.getId(), hotelDomain.getId());
    assertEquals(hotel.getAddress(), hotelDomain.getAddress());
    assertEquals(hotel.getName(), hotelDomain.getName());
    assertEquals(hotel.getRating(), hotelDomain.getRating());
  }

}