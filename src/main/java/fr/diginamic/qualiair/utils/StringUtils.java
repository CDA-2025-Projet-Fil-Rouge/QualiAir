package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.annotation.DoNotInstanciate;

/**
 * Classe utilitaire regroupant des methodes de traitement de texte
 */
@DoNotInstanciate
public class StringUtils {
    private StringUtils() {
    }

    /**
     * Enlève les guillements d'une chaîne de caractère
     *
     * @param string la chaîne à convertir
     * @return la chaîne sans guillemets
     */
    public static String removeQuotes(String string) {
        return string.replace("\"", "");
    }
}
