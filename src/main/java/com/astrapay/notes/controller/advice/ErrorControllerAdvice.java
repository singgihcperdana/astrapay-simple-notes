package com.astrapay.notes.controller.advice;

import com.astrapay.notes.dto.web.response.ApiResponse;
import com.astrapay.notes.exception.NoteNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
class ErrorControllerAdvice {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({MethodArgumentNotValidException.class})
  ResponseEntity<ApiResponse<Void>> methodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    log.error("methodArgumentNotValidException: {}", e.getMessage());
    ApiResponse<Void> response = ApiResponse.<Void>builder().success(false).message(
            "Validation failed: " + e.getBindingResult().getAllErrors().get(0).getDefaultMessage())
        .data(null).build();
    return ResponseEntity.badRequest().body(response);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({Throwable.class, Exception.class})
  ResponseEntity<ApiResponse<Void>> throwable(Throwable e) {
    log.error(e.getClass().getName(), e);
    ApiResponse<Void> response = ApiResponse.<Void>builder().success(false)
        .message("Internal server error: " + e.getMessage()).data(null).build();
    return ResponseEntity.internalServerError().body(response);
  }

  @ExceptionHandler(NoteNotFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleNoteNotFoundException(NoteNotFoundException e) {
    ApiResponse<Void> response = ApiResponse.<Void>builder()
        .success(false)
        .message(e.getMessage())
        .data(null)
        .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

}