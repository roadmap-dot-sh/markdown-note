/*
 * MarkdownService.java
 *
 * Copyright (c) 2025 Nguyen. All rights reserved.
 * This software is the confidential and proprietary information of Nguyen.
 */

package com.example.markdownnote.service;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.springframework.stereotype.Service;

/**
 * MarkdownService.java
 *
 * @author Nguyen
 */
@Service
public class MarkdownService {
    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownService() {
        MutableDataSet options = new MutableDataSet();
        this.parser = Parser.builder(options).build();
        this.renderer = HtmlRenderer.builder(options).build();
    }

    public String renderToHtml(String markdown) {
        if (markdown == null || markdown.trim().isEmpty()) {
            return "<p><em>Empty note</em></p>";
        }

        Node document = parser.parse(markdown);
        return renderer.render(document);
    }

    public String extractPlainText(String markdown) {
        // Simple extraction - remove markdown syntax
        return markdown.replaceAll("[#*_`\\[\\]\\(\\)]", "");
    }
}
