package com.project.textapp.model;

import java.util.List;

public interface TextComponent {
    ElementType getType();
    void add(TextComponent value);
    void remove(TextComponent value);
    List<TextComponent> getChildren();
    String restore();
}
