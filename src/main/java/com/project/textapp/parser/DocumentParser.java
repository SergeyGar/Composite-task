package com.project.textapp.parser;

import com.project.textapp.model.ElementType;
import java.util.regex.Pattern;

public final class DocumentParser extends RegexCompositeParser {
    private static final String PARAGRAPH_REGEX = "\\R|(?<content>[^\\r\\n\\x{85}\\x{2028}\\x{2029}\\x{B}\\x{C}]+)";
    private static final Pattern PARAGRAPHS = Pattern.compile(PARAGRAPH_REGEX);

    public DocumentParser() {
        super(ElementType.DOCUMENT, PARAGRAPHS, new ParagraphParser());
    }
}
