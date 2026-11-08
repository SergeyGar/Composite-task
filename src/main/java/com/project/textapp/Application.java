package com.project.textapp;

import com.project.textapp.exception.TextProcessingException;
import com.project.textapp.io.ClasspathTextReader;
import com.project.textapp.model.TextComponent;
import com.project.textapp.parser.DocumentParser;
import com.project.textapp.service.TextStatisticsService;
import com.project.textapp.service.TextTasksService;
import com.project.textapp.service.impl.TextTasksServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Application {
    private static final Logger LOGGER = LogManager.getLogger(Application.class);
    private static final String INPUT_RESOURCE = "text/input.txt";
    private static final char SORT_LETTER = 'a';

    private Application() {
    }

    public static void main(String[] args) {
        try {
            TextComponent document = new DocumentParser().parse(new ClasspathTextReader().read(INPUT_RESOURCE));
            TextTasksService tasks = new TextTasksServiceImpl();
            TextStatisticsService statistics = new TextStatisticsService();
            System.out.println("Original text:\n" + document.restore());
            System.out.println("Letters: " + statistics.countLetters(document));
            System.out.println("Characters: " + statistics.countCharacters(document));
            System.out.println("Sentences containing duplicate words: "
                    + tasks.countSentencesWithDuplicateWords(document));
            System.out.println("Sentences ordered by occurrences of '" + SORT_LETTER + "':");
            tasks.sortSentencesByLetterCount(document, SORT_LETTER).forEach(System.out::println);
            tasks.swapFirstAndLastLexemes(document);
            System.out.println("Text after swapping first and last lexemes:\n" + document.restore());
            LOGGER.info("Text processing completed");
        } catch (TextProcessingException exception) {
            LOGGER.error("Text processing failed", exception);
            throw exception;
        }
    }
}
