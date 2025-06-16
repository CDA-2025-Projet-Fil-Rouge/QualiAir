package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.exception.ParsedDataException;

public class CoordonneeUtils {
    private CoordonneeUtils() {
    }

    public static String toKey(double lat, double lon) {
        return lat + "|" + lon;
    }

    public static Double toDouble(String coordinate) throws ParsedDataException {
        if (coordinate == null || coordinate.trim().isEmpty()) {
            throw new ParsedDataException("Impossible to convert coordinates to double");
        }
        return Double.parseDouble(coordinate.trim());
    }
}
