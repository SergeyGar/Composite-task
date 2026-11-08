package com.project.textapp.model;

import com.project.textapp.exception.TextProcessingException;
import java.util.List;

public final class TextCharacter implements TextComponent {
    private final int codePoint;

    public TextCharacter(int codePoint) {
        if (!Character.isValidCodePoint(codePoint)) {
            throw new TextProcessingException("Invalid Unicode code point");
        }
        this.codePoint = codePoint;
    }

    @Override
    public ElementType getType() {
        return ElementType.SYMBOL;
    }

    @Override
    public void add(TextComponent value) {
        throw new TextProcessingException("A character cannot have children");
    }

    @Override
    public void remove(TextComponent value) {
        throw new TextProcessingException("A character cannot have children");
    }

    @Override
    public List<TextComponent> getChildren() {
        return List.of();
    }

    @Override
    public String restore() {
        return new String(Character.toChars(codePoint));
    }
}
