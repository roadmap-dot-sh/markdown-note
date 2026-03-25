/*
 * NoteController.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.markdownnote.controller;

import com.example.markdownnote.model.GrammarCheckRequest;
import com.example.markdownnote.model.GrammarCheckResponse;
import com.example.markdownnote.model.Note;
import com.example.markdownnote.service.GrammarCheckService;
import com.example.markdownnote.service.MarkdownService;
import com.example.markdownnote.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NoteController.java
 *
 * @author Nguyen
 */
@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private GrammarCheckService grammarCheckService;

    @Autowired
    private MarkdownService markdownService;

    // Endpoint to check grammar of a note
    @PostMapping("/grammar-check")
    public ResponseEntity<GrammarCheckResponse> checkGrammar(@RequestBody GrammarCheckRequest request) {
        GrammarCheckResponse response = grammarCheckService.checkGrammar(request.getText());
        return ResponseEntity.ok(response);
    }

    // Endpoint to save a note from markdown text
    @PostMapping("/save")
    public ResponseEntity<Note> saveNote(@RequestBody Map<String, String> request) {
        String title = request.get("title");
        String content = request.get("content");

        if (title == null || title.trim().isEmpty()) {
            title = "Untitled Note";
        }

        if (content == null) {
            content = "";
        }

        Note savedNote = noteService.saveNoteFromMarkdown(title, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
    }

    // Endpoint to upload a markdown file
    @PostMapping("/upload")
    public ResponseEntity<Note> uploadMarkdownFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            if (!file.getOriginalFilename().endsWith(".md")) {
                return ResponseEntity.badRequest().build();
            }

            Note savedNote = noteService.saveNoteFromFile(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to list all saved notes
    @GetMapping
    public ResponseEntity<List<Note>> listNotes() {
        List<Note> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    // Endpoint to get a specific note
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable String id) {
        try {
            Note note = noteService.getNoteById(id);
            return ResponseEntity.ok(note);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to get HTML version of a markdown note
    @GetMapping("/{id}/html")
    public ResponseEntity<Map<String, String>> getRenderedNote(@PathVariable String id) {
        try {
            Note note = noteService.getNoteById(id);
            String html = markdownService.renderToHtml(note.getContent());

            Map<String, String> response = new HashMap<>();
            response.put("id", note.getId());
            response.put("title", note.getTitle());
            response.put("html", html);
            response.put("createdAt", note.getCreatedAt().toString());
            response.put("updatedAt", note.getUpdatedAt().toString());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to update a note
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable String id, @RequestBody Map<String, String> request) {
        try {
            String content = request.get("content");
            Note updatedNote = noteService.updateNote(id, content);
            return ResponseEntity.ok(updatedNote);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to delete a note
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        try {
            noteService.deleteNote(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to get rendered HTML directly from markdown text
    @PostMapping("/render")
    public ResponseEntity<Map<String, String>> renderMarkdown(@RequestBody Map<String, String> request) {
        String markdown = request.get("markdown");
        String html = markdownService.renderToHtml(markdown);

        Map<String, String> response = new HashMap<>();
        response.put("html", html);
        response.put("markdown", markdown);

        return ResponseEntity.ok(response);
    }
}
