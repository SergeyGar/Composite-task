package com.project.textapp.service;

import com.project.textapp.exception.TextProcessingException;
import com.project.textapp.model.TextComponent;

public final class TextStatisticsService {
    public long countLetters(TextComponent text) {
        return restore(text).codePoints().filter(Character::isLetter).count();
    }

    /** Counts Unicode code points, including whitespace and punctuation. */
    public long countCharacters(TextComponent text) {
        return restore(text).codePoints().count();
    }

    private String restore(TextComponent text) {
        if (text == null) {
            throw new TextProcessingException("Text must not be null");
        }
        return text.restore();
    }
}
