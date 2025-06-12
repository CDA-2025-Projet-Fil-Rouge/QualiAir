package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.exception.ParsedDataException;

public class RegionUtils {

    public static int toInt(String string) {
        if (string.trim().isEmpty()) {
            throw new ParsedDataException("Le code region est invalide");
        }
        return Integer.parseInt(string.trim());
    }
}
