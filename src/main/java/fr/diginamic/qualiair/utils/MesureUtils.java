package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.dto.InfoCarteCommuneDetailMeteo;
import fr.diginamic.qualiair.dto.InfoCarteCommuneDetailQualiteAir;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            throw new UnnecessaryApiRequestException(String.format("Forecast already exists within range %s / %s for the type %s", DateUtils.toStringCompletePattern(startDate), DateUtils.toStringCompletePattern(endDate), typeReleve));
        }
    }

    public static void ThrowExceptionIfTrue(boolean exists, LocalDateTime startDate, TypeReleve typeReleve) throws UnnecessaryApiRequestException {
        if (exists) {
            throw new UnnecessaryApiRequestException(String.format("Forecast already exists for today (%s) for the type %s", DateUtils.toStringCompletePattern(startDate), typeReleve));
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

    public static List<MesureAir> getMesureAir(Collection<Mesure> mesures) {
        return mesures.stream()
                .filter(m -> m instanceof MesureAir)
                .map(m -> (MesureAir) m)
                .collect(Collectors.toList());
    }

    public static List<MesurePrevision> getMesurePrevision(Collection<Mesure> mesures) {
        return mesures.stream()
                .filter(m -> m instanceof MesurePrevision)
                .map(m -> (MesurePrevision) m)
                .collect(Collectors.toList());
    }

    public static void setDetailMeteo(List<MesurePrevision> latestPrevisions, InfoCarteCommuneDetailMeteo detailMeteo) {
        if (!latestPrevisions.isEmpty()) {
            for (MesurePrevision mp : latestPrevisions) {
                try {
                    NatureMesurePrevision meteo = NatureMesurePrevision.valueOf(mp.getNature().toUpperCase());
                    Map<Double, String> valeurUnite = Map.of(mp.getValeur(), mp.getUnite());
                    detailMeteo.addMeteo(meteo, valeurUnite);
                } catch (IllegalArgumentException e) {
                    continue;
                }
            }
        }
    }

    public static void setDetailAir(List<MesureAir> latestAirs, InfoCarteCommuneDetailQualiteAir detailAir) {
        if (!latestAirs.isEmpty()) {
            for (MesureAir ma : latestAirs) {
                try {
                    AirPolluant polluant = AirPolluant.valueOf(ma.getCodeElement().toUpperCase());
                    detailAir.addIndice(polluant, ma.getIndice());
                } catch (IllegalArgumentException e) {
                    continue;
                }
            }
        }
    }

    /**
     * Crée une mesure air
     *
     * @param codeElement code de l'élément récupéré par la requete
     * @param indice      valeur indexée de la mesure (convention //todo trouver la norme euro d'indexation
     * @param dateReleve  date du relevé
     * @param timeStamp   date d'insertion
     * @return mesure créée
     */
    public static MesureAir createMesureAir(String codeElement, String indice,
                                            LocalDateTime dateReleve, LocalDateTime timeStamp) {
        MesureAir mesure = new MesureAir();
        mesure.setTypeMesure(TypeMesure.RELEVE_AIR);
        mesure.setCodeElement(cleanUpElementCode(codeElement));
        mesure.setIndice(Integer.parseInt(indice));
        mesure.setDateReleve(dateReleve);
        mesure.setDateEnregistrement(timeStamp);

        return mesure;
    }
}
