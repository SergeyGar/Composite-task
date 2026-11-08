package com.project.textapp.parser;

import com.project.textapp.exception.TextProcessingException;
import com.project.textapp.model.ElementType;
import com.project.textapp.model.TextCharacter;
import com.project.textapp.model.TextComponent;
import com.project.textapp.model.TextComposite;

public final class WordParser implements TextParser {
    @Override
    public TextComponent parse(String text) {
        if (text == null) {
            throw new TextProcessingException("Text must not be null");
        }
        TextComposite word = new TextComposite(ElementType.WORD);
        text.codePoints().forEach(cp -> word.add(new TextCharacter(cp)));
        return word;
    }
}
