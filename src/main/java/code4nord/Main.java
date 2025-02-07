package code4nord;

import code4nord.model.WordModel.Occurrence;
import code4nord.model.WordModel.Word;
import code4nord.model.WordModel.WordOccurrenceEntry;
import code4nord.reader.FileReader;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    /**
     * Number of words to be displayed.
     */
    public static final int NUMBER_OF_WORDS = 10;
    //TODO VS file path should be provided as input
    public static final String FILE_PATH = "data/shakespeare_historical_plays.txt";

    public static void main(String[] args) {

        FileReader fileReader = new FileReader();
        WordOccurrenceCounter wordOccurrenceCounter = new WordOccurrenceCounter(fileReader);
        Map<Word, Occurrence> wordByOccurrenceMap = wordOccurrenceCounter.countWords(Path.of(FILE_PATH));
        List<WordOccurrenceEntry> wordOccurrences = wordOccurrenceCounter.getWordsWithMostOccurrences(wordByOccurrenceMap, NUMBER_OF_WORDS);
        LOGGER.log(Level.INFO, "The result, displayed in an aleatory order, of the {0} words with most occurrences are:", NUMBER_OF_WORDS);
        wordOccurrences.forEach(System.out::println);

    }


}