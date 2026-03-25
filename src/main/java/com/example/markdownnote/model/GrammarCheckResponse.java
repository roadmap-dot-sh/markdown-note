/*
 * GrammarCheckResponse.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.markdownnote.model;

import java.util.List;

/**
 * GrammarCheckResponse.java
 *
 * @author Nguyen
 */
public class GrammarCheckResponse {
    private List<GrammarError> errors;
    private String correctedText;

    public GrammarCheckResponse(List<GrammarError> errors, String correctedText) {
        this.errors = errors;
        this.correctedText = correctedText;
    }

    // Getters and Setters
    public List<GrammarError> getErrors() {
        return errors;
    }

    public void setErrors(List<GrammarError> errors) {
        this.errors = errors;
    }

    public String getCorrectedText() {
        return correctedText;
    }

    public void setCorrectedText(String correctedText) {
        this.correctedText = correctedText;
    }

    public static class GrammarError {
        private String message;
        private int offset;
        private int length;
        private List<String> suggestions;

        public GrammarError(String message, int offset, int length, List<String> suggestions) {
            this.message = message;
            this.offset = offset;
            this.length = length;
            this.suggestions = suggestions;
        }

        // Getters and Setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public List<String> getSuggestions() {
            return suggestions;
        }

        public void setSuggestions(List<String> suggestions) {
            this.suggestions = suggestions;
        }
    }
}
