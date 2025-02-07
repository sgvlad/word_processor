package code4nord.utils;

import org.junit.jupiter.api.Test;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextSanitizerTest {

    @Test
    public void testToSanitizedStringWordsWithContractions() {
        String input = "It's a test, isn't it?";
        Stream<String> expected = Stream.of("it", "is", "a", "test", "is", "not", "it");
        Stream<String> result = TextSanitizer.toSanitizedStringWords(input);
        assertEquals(expected.collect(Collectors.toList()), result.collect(Collectors.toList()));
    }

    @Test
    public void testToSanitizedStringWordsWithPunctuation() {
        String input = "Hello, world! This is a test.";
        Stream<String> expected = Stream.of("hello", "world", "this", "is", "a", "test");
        Stream<String> result = TextSanitizer.toSanitizedStringWords(input);
        assertEquals(expected.collect(Collectors.toList()), result.collect(Collectors.toList()));
    }

    @Test
    public void testToSanitizedStringWordsWithMixedCase() {
        String input = "HeLLo WoRLd!";
        Stream<String> expected = Stream.of("hello", "world");
        Stream<String> result = TextSanitizer.toSanitizedStringWords(input);
        assertEquals(expected.collect(Collectors.toList()), result.collect(Collectors.toList()));
    }

    @Test
    public void testToSanitizedStringWordsWithNumbers() {
        String input = "This is a test 123.";
        Stream<String> expected = Stream.of("this", "is", "a", "test", "123");
        Stream<String> result = TextSanitizer.toSanitizedStringWords(input);
        assertEquals(expected.collect(Collectors.toList()), result.collect(Collectors.toList()));
    }

    @Test
    public void testToSanitizedStringWordsWithSpecialCharacters() {
        String input = "Hello @world! #test";
        Stream<String> expected = Stream.of("hello", "world", "test");
        Stream<String> result = TextSanitizer.toSanitizedStringWords(input);
        assertEquals(expected.collect(Collectors.toList()), result.collect(Collectors.toList()));
    }

    @Test
    public void testToSanitizedStringWordsWithMultipleSpaces() {
        String input = "Hello    world";
        Stream<String> expected = Stream.of("hello", "world");
        Stream<String> result = TextSanitizer.toSanitizedStringWords(input);
        assertEquals(expected.collect(Collectors.toList()), result.collect(Collectors.toList()));
    }
}