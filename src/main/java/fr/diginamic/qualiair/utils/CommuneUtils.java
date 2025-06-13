package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.exception.ParsedDataException;

public class CommuneUtils {
    public static int toInt(String string) throws ParsedDataException {
        if (string.trim().isEmpty()) {
            throw new ParsedDataException("Le code insee est invalide");
        }
        return Integer.parseInt(string.trim());
    }
}
