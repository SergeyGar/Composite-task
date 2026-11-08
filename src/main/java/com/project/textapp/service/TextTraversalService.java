package com.project.textapp.service;

import com.project.textapp.model.ElementType;
import com.project.textapp.model.TextComponent;
import java.util.List;

public interface TextTraversalService {
    List<TextComponent> collect(TextComponent root, ElementType type);
}
