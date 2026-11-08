package com.project.textapp.parser;

import com.project.textapp.model.ElementType;
import java.util.regex.Pattern;

public final class LexemeParser extends RegexCompositeParser {
    private static final String WORD_REGEX = "(?<content>[\\p{L}\\p{N}]+)|[^\\p{L}\\p{N}]";
    private static final Pattern WORDS = Pattern.compile(WORD_REGEX);

    public LexemeParser() {
        super(ElementType.LEXEME, WORDS, new WordParser());
    }
}
