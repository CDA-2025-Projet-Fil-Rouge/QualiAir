package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.annotation.DoNotInstanciate;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.exception.ParsedDataException;

/**
 * Classe utilisateur regroupant différentes méthodes de conversion dédiée à l'entité {@link Coordonnee}.
 * Cette class ne doit pas être instanciée.
 */
@DoNotInstanciate
public class CoordonneeUtils {
    private CoordonneeUtils() {
    }

    @Deprecated
    public static String toKey(double lat, double lon) {
        return lat + "|" + lon;
    }

    /**
     * Convertit une chaîne de caractères en double.
     *
     * @param coordinate la chaîne à convertir
     * @return la valeur entière
     * @throws ParsedDataException si la chaîne est vide ou invalide
     */
    public static Double toDouble(String coordinate) throws ParsedDataException {
        if (coordinate == null || coordinate.trim().isEmpty()) {
            throw new ParsedDataException("Impossible to convert coordinates to double");
        }
        return Double.parseDouble(coordinate.trim());
    }
}
