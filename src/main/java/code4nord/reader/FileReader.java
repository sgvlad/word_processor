package code4nord.reader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileReader {

    private static final Logger LOGGER = Logger.getLogger(FileReader.class.getName());

    /**
     * Reads all lines from a file. This is an in-memory reading suitable for small, medium files.
     *
     * @param path to the file.
     * @return All lines from a file.
     */
    public List<String> readAllLines(Path path) {
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            LOGGER.log(Level.INFO, "Successfully read {0} lines from file: {1}", new Object[]{lines.size(), path});
            return lines;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while reading the file.", e);
        }
        return List.of();
    }
}
