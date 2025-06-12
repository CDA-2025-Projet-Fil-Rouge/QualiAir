package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.exception.ParsedDataException;

public class MesureUtils {

    public static int toInt(String string) {
        if (string.trim().isEmpty()) {
            throw new ParsedDataException("Population must be a valid number");
        }
        return Integer.parseInt(string.trim());
    }
}
