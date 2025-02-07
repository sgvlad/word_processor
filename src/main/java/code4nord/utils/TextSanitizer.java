package code4nord.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TextSanitizer {

    private static final String EMPTY_STRING = "";
    public static final String OR_DELIMITER = "|";
    private static final String NON_WORDS_CHARACTERS_REGEX = "\\W+";
    /**
     * Regex for all the punctuation characters like: ! " # $ % & ' ( ) * + , - . / : ; < = > ? @ [ \ ] ^ _ { | } ~
     */
    private static final String PUNCTUATION_CHARACTER_REGEX = "\\p{Punct}";

    /**
     * Map of English words contraction. There are not all contractions and they should be moved in a separate file.
     */
    private static final Map<String, String> CONTRACTIONS_MAP = Map.of("it's", "it is", "you're", "you are", "i'm", "i am", "don't", "do not", "can't", "cannot", "doesn't", "does not",
            "isn't", "is not", "aren't", "are not", "wasn't", "was not", "weren't", "were not");


    /**
     * Compiles the final regular expression string into a Pattern object. The pattern matches any of the contractions from CONTRACTIONS_MAP as whole words.
     * Regular expression form example: "\\b(it's|you're|i'm|don't|can't|doesn't|isn't|aren't|wasn't|weren't| ...)\\b".
     * \\b is a word boundary in regular expressions. It ensures that the pattern matches whole words only, not substrings within longer words.
     */
    private static final Pattern CONTRACTIONS_PATTERN = Pattern.compile("\\b(" +
            String.join(OR_DELIMITER, CONTRACTIONS_MAP.keySet()) + ")\\b");

    /**
     * Given a line from an english text it will first convert it to lowercase, than it will expand the contractions (e.g "it's" will be transformed to "it is"),
     * it will split into words and remove punctuations and empty words.
     */
    public static Stream<String> toSanitizedStringWords(String line) {
        Function<String, String> toLowerCase = String::toLowerCase;
        Function<String, Stream<String>> sanitizeWords = toLowerCase
                .andThen(TextSanitizer::expandContraction)
                .andThen(TextSanitizer::splitToWords)
                .andThen(TextSanitizer::removePunctuation)
                .andThen(TextSanitizer::removeEmptyWords);

        return sanitizeWords.apply(line);
    }


    private static Stream<String> splitToWords(String line) {
        return Arrays.stream(line.split(NON_WORDS_CHARACTERS_REGEX));
    }

    private static Stream<String> removePunctuation(Stream<String> words) {
        return words.map(word -> word.replaceAll(PUNCTUATION_CHARACTER_REGEX, EMPTY_STRING));
    }

    private static Stream<String> removeEmptyWords(Stream<String> words) {
        return words.filter(word -> !word.isEmpty());
    }

    private static String expandContraction(String text) {
        Matcher matcher = CONTRACTIONS_PATTERN.matcher(text);
        StringBuilder stringBuilder = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(stringBuilder, CONTRACTIONS_MAP.get(matcher.group()));
        }
        matcher.appendTail(stringBuilder);
        return stringBuilder.toString();
    }

}
