package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.annotation.DoNotInstanciate;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.exception.ParsedDataException;
import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;

/**
 * Classe utilisateur regroupant différentes méthodes de conversion dédiée à l'entité {@link Commune}.
 * Cette class ne doit pas être instanciée.
 */
@DoNotInstanciate
public class CommuneUtils {
    public CommuneUtils() {
    }

    /**
     * Convertit une chaîne de caractères en entier.
     *
     * @param string la chaîne à convertir
     * @return la valeur entière
     * @throws ParsedDataException si la chaîne est vide ou invalide
     */
    public static int toInt(String string) throws ParsedDataException {
        if (string.trim().isEmpty()) {
            throw new ParsedDataException("Insee code can't be null");
        }
        return Integer.parseInt(string.trim());
    }

    @Deprecated
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

    @Deprecated
    public static String withoutArrondissement(String name) throws ParsedDataException {
        if (name == null) {
            throw new ParsedDataException("Commune name can't be null");
        }
        return name.replaceAll("\\b\\d{1,2}(E|ER)? ARRONDISSEMENT\\b", "").trim();
    }

    public static String normalizeInseeCode(String insee) {
        if (insee == null || insee.trim().isEmpty()) return null;

        if (StringUtils.length(insee) == 5) {
            return insee;
        } else {
            return StringUtils.leftPad(insee, 5, "0");
        }
    }
}
