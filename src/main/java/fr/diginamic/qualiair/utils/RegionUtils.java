package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.annotation.DoNotInstanciate;
import fr.diginamic.qualiair.entity.Region;
import fr.diginamic.qualiair.exception.ParsedDataException;

/**
 * Classe utilisateur regroupant différentes méthodes de manipulation, de conversion et de création dédiée à l'entité {@link Region}.
 * Cette classe ne doit pas être instanciée.
 */
@DoNotInstanciate
public class RegionUtils {
    private RegionUtils() {
    }

    /**
     * Convertie une chaîne de caractère en un entier
     *
     * @param string chaine de caractère
     * @return entier
     * @throws ParsedDataException si la chaîne est vide ou invalide
     */
    public static int toInt(String string) throws ParsedDataException {
        if (string.trim().isEmpty()) {
            throw new ParsedDataException("Le code region est invalide");
        }
        return Integer.parseInt(string.trim());
    }

}
