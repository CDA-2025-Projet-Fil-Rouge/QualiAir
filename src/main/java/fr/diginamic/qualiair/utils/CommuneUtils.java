package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.exception.ParsedDataException;

import java.text.Normalizer;

public class CommuneUtils {
    public CommuneUtils() {
    }

    public static int toInt(String string) throws ParsedDataException {
        if (string.trim().isEmpty()) {
            throw new ParsedDataException("Insee code can't be null");
        }
        return Integer.parseInt(string.trim());
    }

    public static String toNomPostal(String name) throws ParsedDataException {
        if (name == null) {
            throw new ParsedDataException("Commune name can't be null");
        }
        String decomposed = Normalizer.normalize(name, Normalizer.Form.NFD);

        StringBuilder sb = new StringBuilder();
        for (char c : decomposed.toCharArray()) {
            if (Character.getType(c) != Character.NON_SPACING_MARK) {
                sb.append(c);
            }
        }
        String normalized = sb.toString();

        normalized = normalized
                .toLowerCase()
                .replace("saint", "st")
                .replace("œ", "oe").replace("Œ", "OE")
                .replace("æ", "ae").replace("Æ", "AE")
                .replace("-", " ").replace("'", " ")
                .trim();

        return normalized.toUpperCase();
    }
}
