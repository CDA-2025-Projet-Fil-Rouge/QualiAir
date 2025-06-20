package fr.diginamic.qualiair.parser;

import fr.diginamic.qualiair.annotation.DoNotInstanciate;
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
 * Classe utilitaire pour le parsing de fichiers CSV.
 * <p>
 * Cette classe fournit des méthodes statiques permettant de lire un fichier CSV
 * depuis un chemin absolu et de transformer son contenu en liste de lignes ou en
 * objets typés via une fonction de mapping.
 * </p>
 * <p>
 * Cette classe ne doit pas être instanciée.
 * </p>
 */
@DoNotInstanciate
public class CsvParser {

    private CsvParser() {
    }

    /**
     * Lit un fichier à partir de son chemin absolu et retourne son contenu sous forme de liste de lignes.
     *
     * @param filePath chemin absolu du fichier (recommandé : à partir de la racine du projet)
     * @return liste des lignes lues dans le fichier
     * @throws IOException si le fichier est introuvable ou ne peut pas être lu
     */
    public static List<String> parseFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    /**
     * Parse un fichier CSV et convertit chaque ligne en un objet de type {@code T} via une fonction de mapping.
     *
     * @param path     chemin absolu vers le fichier CSV
     * @param isQuoted indique si les valeurs du fichier sont entourées de guillemets
     * @param delim    délimiteur utilisé dans le fichier CSV (ex : "," ou ";")
     * @param mapper   fonction permettant de convertir un tableau de chaînes (ligne découpée) en un objet de type {@code T}
     * @param <T>      type de retour souhaité pour chaque ligne du fichier
     * @return liste des objets de type {@code T} issus du mapping des lignes du fichier
     * @throws IOException si une erreur de lecture du fichier survient
     */
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
