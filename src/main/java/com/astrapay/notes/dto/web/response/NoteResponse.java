package com.astrapay.notes.dto.web.response;

import com.astrapay.notes.enums.NoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class NoteResponse {

  private UUID id;
  private String content;
  private NoteType type;
  private String createdAt;
}
