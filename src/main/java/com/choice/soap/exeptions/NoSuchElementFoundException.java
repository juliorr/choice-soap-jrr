package com.choice.soap.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoSuchElementFoundException extends ResponseStatusException {

  public NoSuchElementFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}
