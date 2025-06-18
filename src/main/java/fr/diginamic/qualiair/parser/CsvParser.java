package fr.diginamic.qualiair.parser;

import fr.diginamic.qualiair.utils.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Csv parser implementation
 */

public class CsvParser {

    private CsvParser() {
    }

    /**
     * Takes a file path a returns a list of lines
     *
     * @param filePath absolute path of the file (recommend project root)
     * @return list of lines
     * @throws IOException erreur finding the file
     */
    public static List<String> parseFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    public static <T> List<T> parseRecensementCsv(String path, boolean isQuoted, String delim, Function<String[], T> mapper) throws IOException {
        List<String> lines;
        if (isQuoted) {
            lines = new ArrayList<>(parseFile(path).stream().map(l -> StringUtils.removeQuotes(l).trim()).toList());
        } else {
            lines = new ArrayList<>(parseFile(path).stream().map(String::trim).toList());
        }
        lines.removeFirst();

        return lines.stream()
                .map(line -> {
                    String[] tokens = line.split(delim);
                    for (int i = 0; i < tokens.length; i++) {
                        tokens[i] = tokens[i].trim();
                    }
                    return tokens;
                })
                .map(mapper)
                .toList();
    }
}
