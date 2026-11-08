package com.project.textapp.parser;

import com.project.textapp.model.ElementType;
import java.util.regex.Pattern;

public final class SentenceParser extends RegexCompositeParser {
    private static final String LEXEME_REGEX = "\\s+|(?<content>\\S+)";
    private static final Pattern LEXEMES = Pattern.compile(LEXEME_REGEX, Pattern.UNICODE_CHARACTER_CLASS);

    public SentenceParser() {
        super(ElementType.SENTENCE, LEXEMES, new LexemeParser());
    }
}
