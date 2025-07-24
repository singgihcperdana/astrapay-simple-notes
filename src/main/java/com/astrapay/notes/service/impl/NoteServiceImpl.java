package com.astrapay.notes.service.impl;

import com.astrapay.notes.enums.NoteType;
import com.astrapay.notes.model.Note;
import com.astrapay.notes.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService {

  private final Map<UUID, Note> noteMap = new LinkedHashMap<>();

  public List<Note> getAllNotes() {
    return new ArrayList<>(noteMap.values());
  }

  public Note addNote(String content, NoteType type) {
    log.info("#addNote Adding new note with content: {}, type: {}", content, type);
    UUID id = UUID.randomUUID();
    Note note = Note.builder().id(id).type(type).content(content).createdAt(new Date()).build();
    noteMap.put(id, note);
    log.info("Add note successfully with id: {}", id);
    return note;
  }

  public boolean deleteNote(UUID id) {
    log.info("#deleteNote Deleting note with id: {}", id);
    return noteMap.remove(id) != null;
  }

  @Override
  public void deleteAllNotes() {
    noteMap.clear();
  }


}
