package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.annotation.DoNotInstanciate;
import fr.diginamic.qualiair.enumeration.TypeAlerte;
import fr.diginamic.qualiair.exception.RouteParamException;

@DoNotInstanciate
public class AlerteUtils {
    private AlerteUtils() {
    }

    public static void validateParams(TypeAlerte type, String code, String message) throws RouteParamException {
        if (type != TypeAlerte.NATIONAL && code == null || code.trim().isEmpty()) {
            throw new RouteParamException("Un code est requis pour le type d'alerte" + type);
        }
        if (message == null || message.trim().isEmpty()) {
            throw new RouteParamException("Le message ne doit pas être vide");
        }
    }
}
