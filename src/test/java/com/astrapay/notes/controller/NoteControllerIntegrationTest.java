package com.astrapay.notes.controller;

import com.astrapay.notes.dto.web.request.NoteRequest;
import com.astrapay.notes.enums.NoteType;
import com.astrapay.notes.service.NoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class NoteControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private NoteService noteService;

  @BeforeEach
  void setUp() {
    noteService.deleteAllNotes();
  }

  @Test
  @DisplayName("GET /notes - should return empty list initially")
  void getNotes_empty() throws Exception {
    mockMvc.perform(get("/notes")).andExpect(status().isOk())
        .andExpect(jsonPath("$.success", is(true))).andExpect(jsonPath("$.data", hasSize(0)));
  }

  @Test
  @DisplayName("POST /notes - should create note successfully")
  void createNote_success() throws Exception {
    NoteRequest request = new NoteRequest();
    request.setContent("Test Note");
    request.setType(NoteType.PERSONAL.name());

    mockMvc.perform(post("/notes").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.data.content", is("Test Note")))
        .andExpect(jsonPath("$.data.type", is("PERSONAL")));
  }

  @Test
  @DisplayName("POST /notes - should fail validation when content is empty")
  void createNote_validationFail() throws Exception {
    NoteRequest request = new NoteRequest();
    request.setContent("");
    request.setType(NoteType.PERSONAL.name());

    mockMvc.perform(post("/notes").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.success", is(false)))
        .andExpect(jsonPath("$.message", containsString("Validation failed")));
  }

  @Test
  @DisplayName("DELETE /notes/{id} - should return not found for invalid id")
  void deleteNote_notFound() throws Exception {
    mockMvc.perform(delete("/notes/{id}", "00000000-0000-0000-0000-000000000000"))
        .andExpect(status().isNotFound()).andExpect(jsonPath("$.success", is(false)))
        .andExpect(jsonPath("$.message", containsString("Note not found")));
  }

  @Test
  @DisplayName("POST & DELETE /notes - full flow")
  void createAndDeleteNote_flow() throws Exception {
    // Create note
    NoteRequest request = new NoteRequest();
    request.setContent("Integration Note");
    request.setType(NoteType.WORK.name());

    ResultActions postResult = mockMvc.perform(
            post("/notes").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isOk())
        .andExpect(jsonPath("$.success", is(true))).andExpect(jsonPath("$.data.id").exists());

    // Extract id
    String response = postResult.andReturn().getResponse().getContentAsString();
    String id = objectMapper.readTree(response).path("data").path("id").asText();

    // Delete note
    mockMvc.perform(delete("/notes/{id}", id)).andExpect(status().isNoContent())
        .andExpect(jsonPath("$.success", is(true)))
        .andExpect(jsonPath("$.message", containsString("deleted")));
  }
} 