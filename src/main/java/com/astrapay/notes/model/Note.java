package com.astrapay.notes.model;

import com.astrapay.notes.enums.NoteType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuperBuilder
public class Note extends BaseAudit {

  private UUID id;

  private String content;

  private NoteType type;

}
