/*
 * GrammarCheckService.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.markdownnote.service;

import com.example.markdownnote.model.GrammarCheckResponse;
import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * GrammarCheckService.java
 *
 * @author Nguyen
 */
@Service
public class GrammarCheckService {
    private final JLanguageTool languageTool;

    public GrammarCheckService() {
        this.languageTool = new JLanguageTool(new BritishEnglish());
    }

    public GrammarCheckResponse checkGrammar(String text) {
        try {
            List<RuleMatch> matches = languageTool.check(text);
            List<GrammarCheckResponse.GrammarError> errors = new ArrayList<>();

            for (RuleMatch match : matches) {
                List<String> suggestions = match.getSuggestedReplacements();
                GrammarCheckResponse.GrammarError error =
                        new GrammarCheckResponse.GrammarError(
                                match.getMessage(),
                                match.getFromPos(),
                                match.getToPos() - match.getFromPos(),
                                suggestions
                        );
                errors.add(error);
            }

            // Simple auto-correction (first suggestion for each error)
            String correctedText = text;
            for (RuleMatch match : matches) {
                if (!match.getSuggestedReplacements().isEmpty()) {
                    String suggestion = match.getSuggestedReplacements().get(0);
                    correctedText = correctedText.substring(0, match.getFromPos()) +
                            suggestion +
                            correctedText.substring(match.getToPos());
                }
            }

            return new GrammarCheckResponse(errors, correctedText);

        } catch (Exception e) {
            throw new RuntimeException("Error checking grammar: " + e.getMessage(), e);
        }
    }
}
