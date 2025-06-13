package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.exception.ParsedDataException;

public class CoordonneeUtils {

    public static String toKey(double lat, double lon) {
        return lat + "|" + lon;
    }

    public static Double toDouble(String coordinate) {
        if (coordinate.trim().isEmpty()) {
            throw new ParsedDataException("coordonnée invalide");
        }
        return Double.parseDouble(coordinate.trim());
    }
}
