package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.exception.FunctionnalException;

public class AirPolluantUtils {
    private AirPolluantUtils() {
    }

    public static int calculateIndice(String codeElement, double valeur) throws FunctionnalException {
        switch (codeElement) {
            case "NO2" -> {
                return polluantToIndexCalculator(valeur, 40, 90, 120, 230, 340);
            }
            case "O3" -> {
                return polluantToIndexCalculator(valeur, 50, 100, 130, 240, 380);
            }
            case "PM10" -> {
                return polluantToIndexCalculator(valeur, 20, 40, 50, 100, 150);
            }
            case "PM25" -> {
                return polluantToIndexCalculator(valeur, 10, 20, 25, 50, 75);
            }
            case "SO2" -> {
                return polluantToIndexCalculator(valeur, 100, 200, 350, 500, 750);
            }
            default -> {
                return 0;
            }


        }
    }


    public static int polluantToIndexCalculator(double value, int... thresholds) throws FunctionnalException {
        if (value < 0 || thresholds.length == 0) {
            throw new FunctionnalException("Invalid parameters");
        }
        int val = Math.toIntExact(Math.round(value));
        int index = 0;
        for (int threshold : thresholds) {
            index++;
            if (val <= threshold) {
                return index;
            }
        }
        return index;
    }
}
