package code4nord;

import code4nord.model.WordModel.Occurrence;
import code4nord.model.WordModel.Word;
import code4nord.model.WordModel.WordOccurrenceEntry;
import code4nord.reader.FileReader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class WordOccurrenceCounter {


    private final FileReader fileReader;

    public WordOccurrenceCounter(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    /**
     * Given a {@link Path} to a file, reads the file and extract all words in a {@link Map} where the
     * key is the word, and the value is the number of its occurrence in the file.
     *
     * @param path to the file.
     * @return A map with all words and their occurrence in the file.
     */
    public Map<Word, Occurrence> countWords(Path path) {
        List<String> lines = this.fileReader.readAllLines(path);
        return lines.stream().flatMap(Word::of).collect(Word.groupByWordAndOccurrence());
    }


    /**
     * Calculates the first {@param numberOfWords} words with most occurrences. They are not returned in a specific order.
     *
     * @param wordToOccurrencesMap a map of {@link Word} and its {@link Occurrence}.
     * @param numberOfWords        that should be taking into consideration.
     * @return a list of {@link WordOccurrenceEntry} composed of a key, represented by a word, and value, represented by its occurrences.
     */
    public List<WordOccurrenceEntry> getWordsWithMostOccurrences(Map<Word, Occurrence> wordToOccurrencesMap, int numberOfWords) {

        PriorityQueue<WordOccurrenceEntry> wordOccurrenceEntryHeap = new PriorityQueue<>(WordOccurrenceEntry.comparingByOccurrence());

        wordToOccurrencesMap.entrySet().stream().map(WordOccurrenceEntry::new).forEach(wordOccurrenceEntry -> {
            wordOccurrenceEntryHeap.offer(wordOccurrenceEntry);
            if (wordOccurrenceEntryHeap.size() > numberOfWords) wordOccurrenceEntryHeap.poll();
        });

        return new ArrayList<>(wordOccurrenceEntryHeap);
    }


}
