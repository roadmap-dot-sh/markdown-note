/*
 * NoteService.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.markdownnote.service;

import com.example.markdownnote.model.Note;
import com.example.markdownnote.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * NoteService.java
 *
 * @author Nguyen
 */
@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    public Note saveNoteFromMarkdown(String title, String content) {
        Note note = new Note(title, content);
        return noteRepository.save(note);
    }

    public Note saveNoteFromFile(MultipartFile file) throws IOException {
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        String title = file.getOriginalFilename().replace(".md", "");
        return saveNoteFromMarkdown(title, content);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(String id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
    }

    public Note updateNote(String id, String content) {
        Note note = getNoteById(id);
        note.setContent(content);
        return noteRepository.save(note);
    }

    public void deleteNote(String id) {
        noteRepository.deleteById(id);
    }
}
