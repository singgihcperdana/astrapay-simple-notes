package com.astrapay.notes.helper;

import com.astrapay.notes.dto.web.response.NoteResponse;
import com.astrapay.notes.model.Note;

import java.text.SimpleDateFormat;

public class ResponseHelper {

  private static final SimpleDateFormat SIMPLE_DATE_FORMAT =
      new SimpleDateFormat("dd/MM/yyyy HH:mm");

  public static NoteResponse toNoteResponse(Note note) {
    String createdAtStr = null;
    if (note.getCreatedAt() != null) {
      createdAtStr = SIMPLE_DATE_FORMAT.format(note.getCreatedAt());
    }
    return NoteResponse.builder().id(note.getId()).content(note.getContent()).type(note.getType())
        .createdAt(createdAtStr).build();
  }

}
