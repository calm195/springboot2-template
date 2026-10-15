package com.example.template.greeting;

/** Immutable HTTP response, separate from future persistence entities. */
public final class GreetingResponse {

  private final String message;

  public GreetingResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
