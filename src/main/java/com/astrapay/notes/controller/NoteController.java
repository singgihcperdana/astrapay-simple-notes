package com.astrapay.notes.controller;

import com.astrapay.notes.dto.web.response.ApiResponse;
import com.astrapay.notes.dto.web.response.NoteResponse;
import com.astrapay.notes.enums.NoteType;
import com.astrapay.notes.helper.ResponseHelper;
import com.astrapay.notes.model.Note;
import com.astrapay.notes.dto.web.request.NoteRequest;
import com.astrapay.notes.service.NoteService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class NoteController {

  private final NoteService service;

  @GetMapping
  @ApiOperation(value = "Get All Notes")
  public ResponseEntity<ApiResponse<List<NoteResponse>>> getNotes() {
    List<NoteResponse> response = service.getAllNotes().stream().map(ResponseHelper::toNoteResponse)
        .collect(Collectors.toList());
    ApiResponse<List<NoteResponse>> apiResponse =
        ApiResponse.<List<NoteResponse>>builder().success(true)
            .message("Notes fetched successfully").data(response).build();
    return ResponseEntity.ok(apiResponse);
  }

  @PostMapping
  @ApiOperation(value = "Create Note")
  public ResponseEntity<ApiResponse<NoteResponse>> createNote(
      @Valid @RequestBody NoteRequest request) {
    Note created = service.addNote(request.getContent(), NoteType.valueOf(request.getType()));
    ApiResponse<NoteResponse> apiResponse =
        ApiResponse.<NoteResponse>builder().success(true).message("Note created successfully")
            .data(ResponseHelper.toNoteResponse(created)).build();
    return ResponseEntity.ok(apiResponse);
  }

  @DeleteMapping("/{id}")
  @ApiOperation(value = "Delete Note")
  public ResponseEntity<ApiResponse<Void>> deleteNote(@PathVariable UUID id) {
    boolean removed = service.deleteNote(id);
    if (!removed) {
      ApiResponse<Void> notFoundResponse =
          ApiResponse.<Void>builder().success(false).message("Note not found").data(null).build();
      return ResponseEntity.status(404).body(notFoundResponse);
    }
    ApiResponse<Void> successResponse =
        ApiResponse.<Void>builder().success(true).message("Note deleted successfully").data(null)
            .build();
    return ResponseEntity.status(204).body(successResponse);
  }

}
