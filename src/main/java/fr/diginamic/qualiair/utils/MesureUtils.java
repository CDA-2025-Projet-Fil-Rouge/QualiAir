package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.entity.TypeMesure;
import fr.diginamic.qualiair.entity.TypeReleve;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class MesureUtils {
    private static final Logger logger = LoggerFactory.getLogger(MesureUtils.class);

    private MesureUtils() {
    }

    public static int toInt(String string) throws ParsedDataException {
        if (string.trim().isEmpty()) {
            throw new ParsedDataException("Population must be a valid number");
        }
        return Integer.parseInt(string.trim());
    }

    public static Double toDouble(String value) throws ParsedDataException {
        if (value == null || value.trim().isEmpty()) {
            throw new ParsedDataException("Impossible to convert mesure value to double");
        }
        return Double.parseDouble(value.trim());
    }

    public static String cleanUpElementCode(String code) {
        return code.replace("code_", "");
    }


    public static void ThrowExceptionIfTrue(boolean exists, LocalDateTime startDate, LocalDateTime endDate, TypeReleve typeReleve) throws UnnecessaryApiRequestException {
        if (exists) {
            throw new UnnecessaryApiRequestException(String.format("Forecast already exists within range %s / %s for the type %s", DateUtils.toString(startDate), DateUtils.toString(endDate), typeReleve));
        }
    }

    public static void addIfNotNull(List<MesurePrevision> mesures, String valeur, TypeReleve type, LocalDateTime releve, LocalDateTime maj, NatureMesurePrevision nature) {

        try {
            if (valeur != null) {
                mesures.add(createMesurePrevision(type, releve, maj, valeur, nature));
            }
        } catch (ParsedDataException e) {
            logger.debug("Couldn't convert value to double for {}, at {}", type, maj);
        }
    }

    public static MesurePrevision createMesurePrevision(TypeReleve typeReleve, LocalDateTime dateReleve, LocalDateTime dateMaj, String valeur, NatureMesurePrevision nature) throws ParsedDataException {

        MesurePrevision mesure = new MesurePrevision();
        mesure.setTypeMesure(TypeMesure.RELEVE_METEO);
        mesure.setTypeReleve(typeReleve);
        mesure.setDateReleve(dateReleve);
        mesure.setDateEnregistrement(dateMaj);
        mesure.setValeur(toDouble(valeur));
        setNatureAndUnite(mesure, nature);

        return mesure;
    }

    public static void setNatureAndUnite(MesurePrevision mesure, NatureMesurePrevision nature) {
        mesure.setNature(nature.toString());
        switch (nature) {
            case HUMIDITY, CLOUD_COVERAGE -> mesure.setUnite("%");
            case TEMPERATURE, TEMPERATURE_MAX, TEMPERATURE_FELT, TEMPERATURE_MIN -> mesure.setUnite("Celcius");
            case PRESSURE -> mesure.setUnite("hpa");
            case VISIBILITY -> mesure.setUnite("km");
            case WIND_SPEED, WIND_SPEED_GUST -> mesure.setUnite("m/s");
            case WIND_ORIENTATION -> mesure.setUnite("degre");
            default -> mesure.setUnite("tbd");
        }
    }
}
