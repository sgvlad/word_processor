package code4nord.model;

import code4nord.utils.TextSanitizer;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordModel {

    /**
     * Wrapper over a string representing a word.
     *
     * @param word as string.
     */
    public record Word(String word) {

        /**
         * Takes a line as string and creates a stream of {@link Word} after sanitizing the strings.
         *
         * @param line as string  from the text.
         * @return a stream of {@link Word}.
         */
        public static Stream<Word> of(String line) {
            return TextSanitizer.toSanitizedStringWords(line).map(Word::new);
        }

        public static Collector<Word, ?, Map<Word, Occurrence>> groupByWordAndOccurrence() {
            return Collectors.groupingBy(Function.identity(), Occurrence.countWordOccurrence());
        }
    }

    /**
     * Wrapper over an int representing the occurrence of a {@link Word} in a text.
     *
     * @param occurrence as int.
     */
    public record Occurrence(int occurrence) implements Comparable<Occurrence> {
        @Override
        public int compareTo(Occurrence other) {
            return Integer.compare(this.occurrence, other.occurrence);
        }

        private static Collector<Word, Object, Occurrence> countWordOccurrence() {
            return Collectors.collectingAndThen(
                    Collectors.summingInt(e -> 1), Occurrence::new);
        }
    }

    /**
     * Representation of a {@link Map.Entry<Word, Occurrence>}.
     *
     * @param word       word in a text.
     * @param occurrence number of occurrence in a text.
     */
    public record WordOccurrenceEntry(Word word, Occurrence occurrence) {
        public WordOccurrenceEntry(Map.Entry<Word, Occurrence> entry) {
            this(entry.getKey(), entry.getValue());
        }

        public static Comparator<? super WordOccurrenceEntry> comparingByOccurrence() {
            return Comparator.comparing(WordOccurrenceEntry::occurrence);
        }

        @Override
        public String toString() {
            return this.word.word + " : " + this.occurrence.occurrence;
        }
    }
}
