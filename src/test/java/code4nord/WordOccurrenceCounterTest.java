package code4nord;

import code4nord.model.WordModel.*;
import code4nord.reader.FileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class WordOccurrenceCounterTest {
    @TempDir
    Path path;
    private FileReader fileReader;
    private WordOccurrenceCounter wordOccurrenceCounter;

    @BeforeEach
    public void setUp() {
        fileReader = Mockito.mock(FileReader.class);
        wordOccurrenceCounter = new WordOccurrenceCounter(fileReader);
    }

    @Test
    public void testCountWords() {
        List<String> lines = Arrays.asList("hello world", "hello");
        when(fileReader.readAllLines(path)).thenReturn(lines);

        Map<Word, Occurrence> expected = new HashMap<>();
        expected.put(new Word("hello"), new Occurrence(2));
        expected.put(new Word("world"), new Occurrence(1));

        Map<Word, Occurrence> result = wordOccurrenceCounter.countWords(path);
        assertEquals(expected, result);
    }

    @Test
    public void testGetWordsWithMostOccurrences() {
        Map<Word, Occurrence> wordToOccurrencesMap = new HashMap<>();
        wordToOccurrencesMap.put(new Word("hello"), new Occurrence(2));
        wordToOccurrencesMap.put(new Word("world"), new Occurrence(1));

        List<WordOccurrenceEntry> expected = new ArrayList<>();
        expected.add(new WordOccurrenceEntry(new Word("world"), new Occurrence(1)));
        expected.add(new WordOccurrenceEntry(new Word("hello"), new Occurrence(2)));

        List<WordOccurrenceEntry> result = wordOccurrenceCounter.getWordsWithMostOccurrences(wordToOccurrencesMap, 2);
        assertEquals(expected, result);
    }

    @Test
    public void testCountWordsWithContractions() {
        List<String> lines = Arrays.asList("it's a test", "it is a test");
        when(fileReader.readAllLines(path)).thenReturn(lines);

        Map<Word, Occurrence> expected = new HashMap<>();
        expected.put(new Word("it"), new Occurrence(2));
        expected.put(new Word("is"), new Occurrence(2));
        expected.put(new Word("a"), new Occurrence(2));
        expected.put(new Word("test"), new Occurrence(2));

        Map<Word, Occurrence> result = wordOccurrenceCounter.countWords(path);
        assertEquals(expected, result);
    }
}