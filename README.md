# Text Processing Application

Java 21 and Maven are required. Run `mvn clean verify` to compile and test,
then `mvn exec:java` to run the program, or run `Application.main` in IntelliJ IDEA.

The program reads UTF-8 text from `src/main/resources/text/input.txt` on the
classpath and demonstrates all operations. Change `SORT_LETTER` in `Application`
to choose the letter used for sorting.

The Composite tree contains documents, paragraphs, sentences, lexemes, words,
and individual Unicode symbols. Whitespace is represented by symbol children;
restoration preserves the original spacing, tabs, line endings, and punctuation.
Read-only child lists protect structure; composites expose controlled mutation.

The parser chain is document → paragraph → sentence → lexeme → word.
Each `DocumentParser` creates five reusable handlers once; parsing never creates
additional parser objects. Each handler delegates the next level to its successor.
Regex patterns are literal constants. Line breaks delimit paragraphs; `.`, `!`,
and `?` delimit sentences (including repeated terminators and closing quotes).
Abbreviations and decimal points receive no special handling. An unfinished final
sentence is included. Empty input and blank lines are preserved.

Task semantics follow the clarification in `task-description.md`:

- Count sentences containing at least one repeated alphanumeric word, ignoring
  case. Multiple repeated words still count that sentence once.
- Sort sentences by occurrences of a given letter, ignoring case. Ties retain
  their original order. Sorting leaves the document unchanged.
- Swap the first and last whole lexemes in each sentence, including punctuation:
  `Hello beautiful world!` becomes `world! beautiful Hello`. Whitespace stays in
  its original slots, so each sentence retains its length. This mutates the tree.

Character counts include whitespace and punctuation and count Unicode code points;
letter counts use `Character.isLetter`. Words follow `[\p{L}\p{N}]+`, as in
the supplied clarification. New operations can use `TextTraversalService` and the
component interface without changing the parsers or entities.
