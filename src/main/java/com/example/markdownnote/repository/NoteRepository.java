/*
 * NoteRepository.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.markdownnote.repository;

import com.example.markdownnote.model.Note;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * NoteRepository.java
 *
 * @author Nguyen
 */
@Repository
public class NoteRepository {
    private final Map<String, Note> notes = new ConcurrentHashMap<>();

    public Note save(Note note) {
        notes.put(note.getId(), note);
        return note;
    }

    public Optional<Note> findById(String id) {
        return Optional.ofNullable(notes.get(id));
    }

    public List<Note> findAll() {
        return new ArrayList<>(notes.values());
    }

    public void deleteById(String id) {
        notes.remove(id);
    }

    public boolean existsById(String id) {
        return notes.containsKey(id);
    }
}
