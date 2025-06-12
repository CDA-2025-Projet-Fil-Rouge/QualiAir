package fr.diginamic.qualiair.parser;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class CsvParser {


    public List<String> parseFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }
}
