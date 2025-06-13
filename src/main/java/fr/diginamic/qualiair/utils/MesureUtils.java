package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.entity.Mesure;
import fr.diginamic.qualiair.exception.ParsedDataException;

public class MesureUtils {

    public static int toInt(String string) {
        if (string.trim().isEmpty()) {
            throw new ParsedDataException("Population must be a valid number");
        }
        return Integer.parseInt(string.trim());
    }

    public static String toKey(String nom, double lat, double lon) {
        String coordKey = CoordonneeUtils.toKey(lat, lon);
        return nom + "_" + coordKey;
    }

    public static String toKey(Mesure mesure) {
        String coordKey = CoordonneeUtils.toKey(mesure.getCoordonnee().getLatitude(), mesure.getCoordonnee().getLongitude());
        return mesure.getNom() + "_" + coordKey;
    }
}
