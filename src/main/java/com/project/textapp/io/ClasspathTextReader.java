package com.project.textapp.io;

import com.project.textapp.exception.TextProcessingException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ClasspathTextReader {
    private static final Logger LOGGER = LogManager.getLogger(ClasspathTextReader.class);

    public String read(String resourceName) {
        if (resourceName == null || resourceName.isBlank()) {
            throw new TextProcessingException("Resource name must not be blank");
        }
        String path = resourceName.startsWith("/") ? resourceName : "/" + resourceName;
        try (InputStream stream = ClasspathTextReader.class.getResourceAsStream(path)) {
            if (stream == null) {
                throw new TextProcessingException("Classpath resource not found: " + resourceName);
            }
            String text = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            LOGGER.info("Read classpath resource {} ({} UTF-16 units)", resourceName, text.length());
            return text;
        } catch (IOException exception) {
            throw new TextProcessingException("Cannot read classpath resource: " + resourceName, exception);
        }
    }
}
