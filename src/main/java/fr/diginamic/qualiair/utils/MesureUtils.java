package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.entity.TypeReleve;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;

import java.time.LocalDateTime;

public class MesureUtils {

    private MesureUtils() {
    }

    public static int toInt(String string) throws ParsedDataException {
        if (string.trim().isEmpty()) {
            throw new ParsedDataException("Population must be a valid number");
        }
        return Integer.parseInt(string.trim());
    }

//    public static String toKey(String nom, double lat, double lon) {
//        String coordKey = CoordonneeUtils.toKey(lat, lon);
//        return nom + "_" + coordKey;
//    }
//
//    public static String toKey(Mesure mesure) {
//        String coordKey = CoordonneeUtils.toKey();
//        return mesure.getTypeMesure() + "_" + coordKey;
//    }

    public static String cleanUpElementCode(String code) {
        return code.replace("code_", "");
    }


    public static void ThrowExceptionIfTrue(boolean exists, LocalDateTime startDate, LocalDateTime endDate, TypeReleve typeReleve) throws UnnecessaryApiRequestException {
        if (exists) {
            throw new UnnecessaryApiRequestException(String.format("Forecast already exists within range %s / %s for the type %s", DateUtils.toString(startDate), DateUtils.toString(endDate), typeReleve));
        }
    }
}
