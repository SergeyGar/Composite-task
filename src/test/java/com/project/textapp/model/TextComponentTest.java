package com.project.textapp.model;

import com.project.textapp.exception.TextProcessingException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextComponentTest {
    @Test
    void compositeSupportsControlledMutation() {
        TextComposite word = new TextComposite(ElementType.WORD);
        TextCharacter first = new TextCharacter('a');
        word.add(first);
        word.add(new TextCharacter('b'));
        assertEquals("ab", word.restore());
        word.swapChildren(0, 1);
        assertEquals("ba", word.restore());
        word.remove(first);
        assertEquals("b", word.restore());
        assertThrows(UnsupportedOperationException.class, () -> word.getChildren().clear());
        assertThrows(TextProcessingException.class, () -> word.add(null));
        assertThrows(TextProcessingException.class, () -> word.add(word));
        assertThrows(TextProcessingException.class, () -> word.swapChildren(-1, 0));
        assertThrows(TextProcessingException.class, () -> new TextComposite(ElementType.SYMBOL));
    }

    @Test
    void characterIsALeafAndSupportsSupplementaryCharacters() {
        TextCharacter character = new TextCharacter(0x1F600);
        assertEquals("😀", character.restore());
        assertEquals(ElementType.SYMBOL, character.getType());
        assertTrue(character.getChildren().isEmpty());
        assertThrows(TextProcessingException.class, () -> character.add(character));
        assertThrows(TextProcessingException.class, () -> character.remove(character));
        assertThrows(TextProcessingException.class, () -> new TextCharacter(-1));
    }
}
