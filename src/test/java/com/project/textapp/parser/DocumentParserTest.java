package com.project.textapp.parser;

import com.project.textapp.exception.TextProcessingException;
import com.project.textapp.model.ElementType;
import com.project.textapp.model.TextComponent;
import com.project.textapp.service.impl.TextTraversalServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class DocumentParserTest {
    private final DocumentParser parser = new DocumentParser();
    private final TextTraversalServiceImpl traversal = new TextTraversalServiceImpl();

    @ParameterizedTest
    @ValueSource(strings = {"", " \t ", "\r\n\r\n", "  Hello\tworld!  Next?\r\n\r\nLast fragment  ",
            "Привет, мир! 😀 Test.", "What?! Really... Yes!", "\"Hello!\" (Goodbye.)",
            ".!?", "a\u0085b\u2028c\u2029d\u000Be\u000Cf", "One.Two!Three?"})
    void restoresEveryCharacter(String source) {
        assertEquals(source, parser.parse(source).restore());
    }

    @Test
    void buildsAllCompositeLevels() {
        TextComponent document = parser.parse("Hello, world! Next?\nLast fragment");
        assertEquals(ElementType.DOCUMENT, document.getType());
        assertEquals(2, traversal.collect(document, ElementType.PARAGRAPH).size());
        assertEquals(3, traversal.collect(document, ElementType.SENTENCE).size());
        assertEquals(5, traversal.collect(document, ElementType.LEXEME).size());
        assertEquals(5, traversal.collect(document, ElementType.WORD).size());
        assertFalse(traversal.collect(document, ElementType.SYMBOL).isEmpty());
    }

    @Test
    void reusesChainWithoutLeakingPreviousResults() {
        TextComponent first = parser.parse("First.");
        assertEquals("Second!", parser.parse("Second!").restore());
        assertEquals("First.", first.restore());
        assertThrows(TextProcessingException.class, () -> parser.parse(null));
    }
}
