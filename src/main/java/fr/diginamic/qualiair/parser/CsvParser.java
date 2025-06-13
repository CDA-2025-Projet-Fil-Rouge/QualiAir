package fr.diginamic.qualiair.parser;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Csv parser implementation
 */
@Component
public class CsvParser {


    /**
     * Takes a file path a returns a list of lines
     *
     * @param filePath absolute path of the file (recommend project root)
     * @return list of lines
     * @throws IOException erreur finding the file
     */
    public List<String> parseFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }
}
