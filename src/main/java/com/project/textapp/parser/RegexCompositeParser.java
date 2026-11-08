package com.project.textapp.parser;

import com.project.textapp.exception.TextProcessingException;
import com.project.textapp.model.ElementType;
import com.project.textapp.model.TextCharacter;
import com.project.textapp.model.TextComponent;
import com.project.textapp.model.TextComposite;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Each handler splits one level and delegates content to its next handler. */
abstract class RegexCompositeParser implements TextParser {
    private final ElementType type;
    private final Pattern tokens;
    private final TextParser next;

    protected RegexCompositeParser(ElementType type, Pattern tokens, TextParser next) {
        this.type = type;
        this.tokens = tokens;
        this.next = next;
    }

    @Override
    public final TextComponent parse(String text) {
        if (text == null) {
            throw new TextProcessingException("Text must not be null");
        }
        TextComposite result = new TextComposite(type);
        Matcher matcher = tokens.matcher(text);
        int end = 0;
        while (matcher.find()) {
            if (matcher.start() != end) {
                throw new TextProcessingException("Unrecognized text at position " + end);
            }
            String content = matcher.group("content");
            if (content == null) {
                matcher.group().codePoints().forEach(cp -> result.add(new TextCharacter(cp)));
            } else {
                result.add(next.parse(content));
            }
            end = matcher.end();
        }
        if (end != text.length()) {
            throw new TextProcessingException("Unrecognized text at position " + end);
        }
        return result;
    }
}
