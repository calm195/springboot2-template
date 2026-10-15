package com.example.template.shared.api;

/** Stable error contract; internal exception details must never be returned to clients. */
public final class ApiError {

  private final String code;
  private final String message;

  public ApiError(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
