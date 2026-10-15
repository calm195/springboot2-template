package com.example.template.greeting;

import org.springframework.stereotype.Service;

/** Example application service; business rules belong here rather than in the controller. */
@Service
public class GreetingService {

  public GreetingResponse greet(String name) {
    return new GreetingResponse("Hello, " + name.trim() + "!");
  }
}
