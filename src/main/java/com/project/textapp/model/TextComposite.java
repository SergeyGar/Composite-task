package com.project.textapp.model;

import com.project.textapp.exception.TextProcessingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TextComposite implements TextComponent {
    private final ElementType type;
    private final List<TextComponent> children = new ArrayList<>();

    public TextComposite(ElementType type) {
        if (type == null || type == ElementType.SYMBOL) {
            throw new TextProcessingException("A composite requires a non-symbol type");
        }
        this.type = type;
    }

    @Override
    public ElementType getType() {
        return type;
    }

    @Override
    public void add(TextComponent value) {
        if (value == null || value == this) {
            throw new TextProcessingException("Invalid child component");
        }
        children.add(value);
    }

    @Override
    public void remove(TextComponent value) {
        children.remove(value);
    }

    @Override
    public List<TextComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void swapChildren(int first, int last) {
        if (first < 0 || last < 0 || first >= children.size() || last >= children.size()) {
            throw new TextProcessingException("Child index is out of bounds");
        }
        Collections.swap(children, first, last);
    }

    @Override
    public String restore() {
        StringBuilder result = new StringBuilder();
        for (TextComponent child : children) {
            result.append(child.restore());
        }
        return result.toString();
    }
}
