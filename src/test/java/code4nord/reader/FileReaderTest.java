package code4nord.reader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FileReaderTest {

    private FileReader fileReader;
    private Path mockPath;
    private final Logger logger = Logger.getLogger(FileReader.class.getName());

    @BeforeEach
    public void setUp() {
        fileReader = new FileReader();
        mockPath = mock(Path.class);
    }

    @Test
    public void testReadAllLinesSuccess() {
        List<String> expectedLines = List.of("line1", "line2", "line3");
        try (MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {
            filesMock.when(() -> Files.readAllLines(mockPath, StandardCharsets.UTF_8)).thenReturn(expectedLines);

            List<String> result = fileReader.readAllLines(mockPath);
            assertEquals(expectedLines, result);
        }
    }

    @Test
    public void testReadAllLinesIOException() {
        try (MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {
            filesMock.when(() -> Files.readAllLines(mockPath, StandardCharsets.UTF_8)).thenThrow(new IOException("Test IOException"));

            List<String> result = fileReader.readAllLines(mockPath);
            assertEquals(List.of(), result);
        }
    }
}