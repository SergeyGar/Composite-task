package com.project.textapp.io;

import com.project.textapp.exception.TextProcessingException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClasspathTextReaderTest {
    private final ClasspathTextReader reader = new ClasspathTextReader();

    @Test
    void readsUtf8FromClasspathWithEitherPathStyle() {
        assertEquals("Привет 😀!\n", reader.read("text/utf8.txt"));
        assertEquals(reader.read("text/utf8.txt"), reader.read("/text/utf8.txt"));
        assertTrue(reader.read("text/input.txt").contains("Hello beautiful world!"));
    }

    @Test
    void reportsMissingAndInvalidResourcesWithCustomException() {
        assertThrows(TextProcessingException.class, () -> reader.read("missing.txt"));
        assertThrows(TextProcessingException.class, () -> reader.read(null));
        assertThrows(TextProcessingException.class, () -> reader.read(" "));
    }
}
