package com.project.textapp.service;

import com.project.textapp.exception.TextProcessingException;
import com.project.textapp.model.TextComponent;
import com.project.textapp.parser.DocumentParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextStatisticsServiceTest {
    @Test
    void countsUnicodeLettersAndCodePointsIncludingWhitespace() {
        TextComponent document = new DocumentParser().parse("Aя𐐀😀 1!\r\n");
        TextStatisticsService service = new TextStatisticsService();
        assertEquals(3, service.countLetters(document));
        assertEquals(9, service.countCharacters(document));
        assertEquals(0, service.countCharacters(new DocumentParser().parse("")));
        assertThrows(TextProcessingException.class, () -> service.countLetters(null));
        assertThrows(TextProcessingException.class, () -> service.countCharacters(null));
    }
}
