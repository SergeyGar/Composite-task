package com.project.textapp.service.impl;

import com.project.textapp.exception.TextProcessingException;
import com.project.textapp.model.ElementType;
import com.project.textapp.model.TextComponent;
import com.project.textapp.service.TextTraversalService;
import java.util.ArrayList;
import java.util.List;

public final class TextTraversalServiceImpl implements TextTraversalService {
    @Override
    public List<TextComponent> collect(TextComponent root, ElementType type) {
        if (root == null || type == null) {
            throw new TextProcessingException("Root and element type must not be null");
        }
        List<TextComponent> result = new ArrayList<>();
        collectInto(root, type, result);
        return List.copyOf(result);
    }

    private void collectInto(TextComponent node, ElementType type, List<TextComponent> result) {
        if (node.getType() == type) {
            result.add(node);
        }
        for (TextComponent child : node.getChildren()) {
            collectInto(child, type, result);
        }
    }
}
