package com.project.textapp.service.impl;

import com.project.textapp.exception.TextProcessingException;
import com.project.textapp.model.ElementType;
import com.project.textapp.model.TextComponent;
import com.project.textapp.model.TextComposite;
import com.project.textapp.service.TextTasksService;
import com.project.textapp.service.TextTraversalService;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class TextTasksServiceImpl implements TextTasksService {
    private final TextTraversalService traversal = new TextTraversalServiceImpl();

    @Override
    public long countSentencesWithDuplicateWords(TextComponent document) {
        return traversal.collect(document, ElementType.SENTENCE).stream()
                .filter(this::hasDuplicate).count();
    }

    private boolean hasDuplicate(TextComponent sentence) {
        Set<String> seen = new HashSet<>();
        for (TextComponent word : traversal.collect(sentence, ElementType.WORD)) {
            if (!seen.add(word.restore().toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> sortSentencesByLetterCount(TextComponent document, char letter) {
        if (!Character.isLetter(letter)) {
            throw new TextProcessingException("The sort character must be a letter");
        }
        return traversal.collect(document, ElementType.SENTENCE).stream()
                .sorted(Comparator.comparingLong(sentence -> countLetter(sentence.restore(), letter)))
                .map(TextComponent::restore).toList();
    }

    private long countLetter(String text, char letter) {
        return text.toLowerCase(Locale.ROOT).codePoints()
                .filter(cp -> cp == Character.toLowerCase(letter)).count();
    }

    @Override
    public void swapFirstAndLastLexemes(TextComponent document) {
        for (TextComponent sentence : traversal.collect(document, ElementType.SENTENCE)) {
            List<TextComponent> children = sentence.getChildren();
            int first = -1;
            int last = -1;
            for (int index = 0; index < children.size(); index++) {
                if (children.get(index).getType() == ElementType.LEXEME) {
                    if (first == -1) {
                        first = index;
                    }
                    last = index;
                }
            }
            if (first != last) {
                if (!(sentence instanceof TextComposite composite)) {
                    throw new TextProcessingException("Swapping requires a mutable sentence composite");
                }
                composite.swapChildren(first, last);
            }
        }
    }
}
