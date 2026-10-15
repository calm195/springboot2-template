package com.example.template.greeting;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/** Exercises the real Spring wiring, method validation, error mapping, and actuator exposure. */
@SpringBootTest
@AutoConfigureMockMvc
class GreetingApiTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void usesDefaultName() throws Exception {
    mockMvc
        .perform(get("/api/v1/greetings"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Hello, World!"));
  }

  @Test
  void trimsName() throws Exception {
    mockMvc
        .perform(get("/api/v1/greetings").param("name", " Codex "))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Hello, Codex!"));
  }

  @Test
  void rejectsBlankName() throws Exception {
    mockMvc
        .perform(get("/api/v1/greetings").param("name", "   "))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("INVALID_REQUEST"))
        .andExpect(jsonPath("$.message").value("Request validation failed"));
  }

  @Test
  void rejectsOversizedName() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/greetings")
                .param("name", String.join("", java.util.Collections.nCopies(81, "a"))))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
  }

  @Test
  void exposesHealthWithoutDetails() throws Exception {
    mockMvc
        .perform(get("/actuator/health"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("UP"))
        .andExpect(jsonPath("$.components").doesNotExist());
  }

  @Test
  void hidesEnvironmentEndpoint() throws Exception {
    mockMvc.perform(get("/actuator/env")).andExpect(status().isNotFound());
  }

  @Test
  void preservesMethodNotAllowedStatusAndHeader() throws Exception {
    mockMvc
        .perform(post("/api/v1/greetings"))
        .andExpect(status().isMethodNotAllowed())
        .andExpect(header().string("Allow", "GET"))
        .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
  }
}
