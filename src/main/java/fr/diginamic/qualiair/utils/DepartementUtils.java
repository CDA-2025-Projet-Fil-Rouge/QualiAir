package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.annotation.DoNotInstanciate;
import fr.diginamic.qualiair.entity.Departement;
import org.apache.commons.lang3.StringUtils;

/**
 * Classe utilisateur regroupant différentes méthodes de manipulation, de conversion et de création dédiée à l'entité {@link Departement}.
 * Cette classe ne doit pas être instanciée.
 */
@DoNotInstanciate
public class DepartementUtils {
    private DepartementUtils() {
    }

    public static String normalizeCodeDetp(String code) {
        if (code == null || code.trim().isEmpty()) return null;

        if (StringUtils.length(code) >= 2) {
            return code;
        } else {
            return StringUtils.leftPad(code, 2, "0");
        }
    }
}
