# Text Processing Application

## Overview

Create an application that parses text from a file and allows performing three different operations on the text.

## Requirements

### Text Representation
- The parsed text must be represented as an object containing, for example, paragraphs, sentences, lexemes, words, and symbols. A **lexeme** is a part of text delimited by whitespace characters. Use the **Composite** pattern.
- Entity classes (classes containing information) must not be overloaded with logic methods.
- The parsed text must be reconstructable in its original form. Spaces and tab characters may be replaced with a single space during parsing.

### Counting
- Count the number of letters and characters in the text.

### Parsing
- Regular expressions must be used to split the text into components. Regular expressions for the application must be defined as literal constants.
- The code that performs the splitting of text into constituent parts must be implemented as **parser classes** using the **Chain of Responsibility** pattern.
- When developing the parsers that parse the text, the number of created parser objects must be kept under control.

### Logging and Restrictions
- Use **Log4J2**, **SLF4J**, etc. for logging.
- The methods of the `Objects` class are **forbidden**.
- The **Lombok** library is **forbidden**.
- Use a **custom exception class** for exceptional situations.
- **Unit tests are mandatory**.
- The created application must allow implementing the group of text-processing tasks (listed below) **without "rewriting" the existing code**.

## General Tasks

1. Find the largest number of sentences that contain identical words.
2. Output all sentences of the given text in ascending order of the number of a given letter in each of them.
3. In each sentence of the text, swap the first lexeme with the last one, without changing the length of the sentence.

## Implementation Note

The **Composite** pattern MUST be used to implement this task.

For example it can be "public class TextCharacter implements TextComponent
public enum ElementType {
DOCUMENT,
PARAGRAPH,
SENTENCE,
LEXEME,
WORD,
SYMBOL
}

public interface TextComponent {
public ElementType getType();
public void add(TextComponent value);
public void remove(TextComponent value);
List<TextComponent> getChildren();
String restore();
}"


Clarification:
package com.project.textapp.service.impl;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.project.textapp.model.TextComponent;
import com.project.textapp.model.ElementType;
import com.project.textapp.model.TextComposite;
import com.project.textapp.service.TextTasksService;

public class TextTasksServiceImpl implements TextTasksService {
private static final Pattern WORD = Pattern.compile("[\\p{L}\\p{N}]+");
private final TextTraversalServiceImpl traversal = new TextTraversalServiceImpl();

    public long countSentencesWithDuplicateWords(TextComponent document) {
        return traversal.collect(document, ElementType.SENTENCE).stream().filter(this::hasDuplicate).count();
    }
    private boolean hasDuplicate(TextComponent sentence) {
        Set<String> seen = new HashSet<>();
        Matcher matcher = WORD.matcher(sentence.restore().toLowerCase(Locale.ROOT));
        while (matcher.find()) if (!seen.add(matcher.group())) return true;
        return false;
    }
    public List<String> sortSentencesByLetterCount(TextComponent document, char letter) {
        List<TextComponent> sentences = traversal.collect(document, ElementType.SENTENCE);
        return sentences.stream().sorted(Comparator.comparingLong(s -> countLetter(s.restore(), letter)))
                .map(TextComponent::restore).toList();
    }
    private long countLetter(String text, char letter) {
        return text.toLowerCase(Locale.ROOT).chars().filter(c -> c == Character.toLowerCase(letter)).count();
    }
    public void swapFirstAndLastLexemes(TextComponent document) {
        for (TextComponent sentence : traversal.collect(document, ElementType.SENTENCE)) {
            List<TextComponent> lexemes = sentence.getChildren();
            if (lexemes.size() > 1) {
                TextComponent first = lexemes.getFirst(), last = lexemes.getLast();
                sentence.getChildren().set(0,last);
                sentence.getChildren().set(sentence.getChildren().size()-1, first);
            }
        }
    }
}