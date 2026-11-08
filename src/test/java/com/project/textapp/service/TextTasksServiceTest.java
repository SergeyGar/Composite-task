package com.project.textapp.service;

import com.project.textapp.exception.TextProcessingException;
import com.project.textapp.model.ElementType;
import com.project.textapp.model.TextComponent;
import com.project.textapp.parser.DocumentParser;
import com.project.textapp.service.impl.TextTasksServiceImpl;
import com.project.textapp.service.impl.TextTraversalServiceImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextTasksServiceTest {
    private final DocumentParser parser = new DocumentParser();
    private final TextTasksService tasks = new TextTasksServiceImpl();

    @Test
    void countsEachSentenceWithDuplicatesOnceIgnoringCaseAndPunctuation() {
        TextComponent document = parser.parse("Word, WORD word! No duplicates here. 42 42? Мир мир! word.");
        assertEquals(3, tasks.countSentencesWithDuplicateWords(document));
        assertEquals(0, tasks.countSentencesWithDuplicateWords(parser.parse("Same. SAME!")));
    }

    @Test
    void sortsIgnoringCaseWithStableTiesWithoutMutatingText() {
        String source = "Aaa! Bee. Cat? Dog! aA.";
        TextComponent document = parser.parse(source);
        assertEquals(List.of("Bee.", "Dog!", "Cat?", "aA.", "Aaa!"),
                tasks.sortSentencesByLetterCount(document, 'A'));
        assertEquals(source, document.restore());
    }

    @Test
    void swapsWholeLexemesAndPreservesEachSentenceLengthAndWhitespace() {
        String source = "  Hello\tbeautiful  world! Single.\r\nFirst last?  ";
        TextComponent document = parser.parse(source);
        TextTraversalService traversal = new TextTraversalServiceImpl();
        List<Integer> lengths = traversal.collect(document, ElementType.SENTENCE).stream()
                .map(sentence -> sentence.restore().length()).toList();
        tasks.swapFirstAndLastLexemes(document);
        assertEquals("  world!\tbeautiful  Hello Single.\r\nlast? First  ", document.restore());
        assertEquals(lengths, traversal.collect(document, ElementType.SENTENCE).stream()
                .map(sentence -> sentence.restore().length()).toList());
        tasks.swapFirstAndLastLexemes(document);
        assertEquals(source, document.restore());
    }

    @Test
    void handlesEmptyTextAndRejectsInvalidArguments() {
        TextComponent empty = parser.parse("");
        assertEquals(0, tasks.countSentencesWithDuplicateWords(empty));
        assertEquals(List.of(), tasks.sortSentencesByLetterCount(empty, 'a'));
        tasks.swapFirstAndLastLexemes(empty);
        assertEquals("", empty.restore());
        assertThrows(TextProcessingException.class, () -> tasks.countSentencesWithDuplicateWords(null));
        assertThrows(TextProcessingException.class, () -> tasks.sortSentencesByLetterCount(empty, '1'));
        assertThrows(TextProcessingException.class, () -> tasks.swapFirstAndLastLexemes(null));
    }
}
