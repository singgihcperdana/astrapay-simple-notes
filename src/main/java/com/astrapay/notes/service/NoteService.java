package com.astrapay.notes.service;

import com.astrapay.notes.enums.NoteType;
import com.astrapay.notes.model.Note;
import com.astrapay.notes.exception.NoteNotFoundException;

import java.util.List;
import java.util.UUID;

public interface NoteService {

  List<Note> getAllNotes();

  Note addNote(String note, NoteType type);

  boolean deleteNote(UUID id) throws NoteNotFoundException;

  void deleteAllNotes();

}
