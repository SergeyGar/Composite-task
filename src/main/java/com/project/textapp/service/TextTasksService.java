package com.project.textapp.service;

import com.project.textapp.model.TextComponent;
import java.util.List;

public interface TextTasksService {
    long countSentencesWithDuplicateWords(TextComponent document);
    List<String> sortSentencesByLetterCount(TextComponent document, char letter);
    void swapFirstAndLastLexemes(TextComponent document);
}
