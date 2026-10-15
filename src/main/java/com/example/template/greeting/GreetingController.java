package com.example.template.greeting;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Versioned API boundary: validates input and delegates business logic through constructor
 * injection.
 */
@Validated
@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingController {

  private final GreetingService greetingService;

  public GreetingController(GreetingService greetingService) {
    this.greetingService = greetingService;
  }

  @GetMapping
  public GreetingResponse greet(
      @RequestParam(defaultValue = "World") @NotBlank @Size(max = 80) String name) {
    return greetingService.greet(name);
  }
}
