package com.astrapay.notes.dto.web.request;

import com.astrapay.notes.enums.NoteType;
import com.astrapay.notes.validator.annotation.ValidEnum;
import com.astrapay.notes.validator.group.Latest;
import lombok.Data;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@GroupSequence({NoteRequest.class, Latest.class})
public class NoteRequest {

  @NotBlank(message = "Content must not be empty") private String content;

  @NotNull(message = "Type must not be null")
  @ValidEnum(enumClass = NoteType.class, message = "NoteType must be PERSONAL,WORK,REMINDER or OTHER", groups = Latest.class)
  private String type;

}
