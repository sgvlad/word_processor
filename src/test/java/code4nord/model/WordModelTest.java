package code4nord.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static code4nord.model.WordModel.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordModelTest {

    @Test
    public void testWordOf() {
        String input = "It's a test, isn't it?";
        List<Word> expected = List.of(
                new Word("it"), new Word("is"), new Word("a"),
                new Word("test"), new Word("is"), new Word("not"),
                new Word("it")
        );
        List<Word> result = Word.of(input).collect(Collectors.toList());
        assertEquals(expected, result);
    }

    @Test
    public void testGroupByWordAndOccurrence() {
        List<Word> words = List.of(
                new Word("test"), new Word("test"),
                new Word("example"), new Word("example"), new Word("example")
        );
        Map<Word, Occurrence> expected = Map.of(
                new Word("test"), new Occurrence(2),
                new Word("example"), new Occurrence(3)
        );
        Map<Word, Occurrence> result = words.stream()
                .collect(Word.groupByWordAndOccurrence());

        assertEquals(expected, result);
    }

    @Test
    public void testOccurrenceCompareTo() {
        Occurrence occurrence1 = new Occurrence(1);
        Occurrence occurrence2 = new Occurrence(2);

        assertEquals(-1, occurrence1.compareTo(occurrence2));
        assertEquals(1, occurrence2.compareTo(occurrence1));
        assertEquals(0, occurrence1.compareTo(new Occurrence(1)));
    }

    @Test
    public void testWordOccurrenceEntryToString() {
        Word word = new Word("test");
        Occurrence occurrence = new Occurrence(3);
        WordOccurrenceEntry entry = new WordOccurrenceEntry(word, occurrence);

        assertEquals("test : 3", entry.toString());
    }

    @Test
    public void testWordOccurrenceEntryComparingByOccurrence() {
        WordOccurrenceEntry entry1 = new WordOccurrenceEntry(
                new Word("test1"), new Occurrence(1));
        WordOccurrenceEntry entry2 = new WordOccurrenceEntry(
                new Word("test2"), new Occurrence(2));

        assertEquals(-1, WordOccurrenceEntry.comparingByOccurrence().compare(entry1, entry2));
        assertEquals(1, WordOccurrenceEntry.comparingByOccurrence().compare(entry2, entry1));
        assertEquals(0, WordOccurrenceEntry.comparingByOccurrence().compare(entry1, new WordOccurrenceEntry(new Word("test1"), new Occurrence(1))));
    }
}