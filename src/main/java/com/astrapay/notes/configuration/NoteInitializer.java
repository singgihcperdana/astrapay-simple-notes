package com.astrapay.notes.configuration;

import com.astrapay.notes.enums.NoteType;
import com.astrapay.notes.service.NoteService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
class NoteInitializer implements ApplicationListener<ContextRefreshedEvent> {

  private final NoteService noteService;

  public NoteInitializer(NoteService noteService) {
    this.noteService = noteService;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    noteService.addNote("Buy groceries", NoteType.PERSONAL);
    noteService.addNote("Finish project report", NoteType.WORK);
    noteService.addNote("Doctor appointment", NoteType.REMINDER);
    noteService.addNote("Team meeting at 10am", NoteType.WORK);
    noteService.addNote("Call mom", NoteType.PERSONAL);
    noteService.addNote("Backup files", NoteType.OTHER);
  }
}