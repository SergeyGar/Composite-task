package com.project.textapp.parser;

import com.project.textapp.model.ElementType;
import java.util.regex.Pattern;

public final class ParagraphParser extends RegexCompositeParser {
    private static final String SENTENCE_REGEX = "\\s+|(?<content>[^\\s.!?][^.!?]*[.!?]+[\"'’”»)]*|[.!?]+[\"'’”»)]*|[^\\s.!?][^.!?]*$)";
    private static final Pattern SENTENCES = Pattern.compile(SENTENCE_REGEX, Pattern.UNICODE_CHARACTER_CLASS);

    public ParagraphParser() {
        super(ElementType.PARAGRAPH, SENTENCES, new SentenceParser());
    }
}
