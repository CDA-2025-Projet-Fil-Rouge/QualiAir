package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.annotation.DoNotInstanciate;
import fr.diginamic.qualiair.dto.carte.DetailAir;
import fr.diginamic.qualiair.dto.carte.DetailMeteo;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.exception.FunctionnalException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static fr.diginamic.qualiair.utils.AirPolluantUtils.calculateIndice;

/**
 * Classe utilitaire regroupant différentes méthodes de manipulation,
 * de conversion et de création liées aux entités {@link Mesure}, {@link MesureAir},
 * {@link MesurePrevision} ainsi qu'à leur traitement métier.
 * Cette classe ne doit pas être instanciée.
 */
@DoNotInstanciate
public class MesureUtils {
    private static final Logger logger = LoggerFactory.getLogger(MesureUtils.class);

    private MesureUtils() {
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
            throw new ParsedDataException("Population must be a valid number");
        }
        return Integer.parseInt(string.trim());
    }

    /**
     * Convertit une chaîne en double.
     *
     * @param value la chaîne représentant une valeur numérique
     * @return la valeur double
     * @throws ParsedDataException si la chaîne est vide ou invalide
     */
    public static Double toDouble(String value) throws ParsedDataException {
        if (value == null || value.trim().isEmpty()) {
            throw new ParsedDataException("Impossible to convert mesure value to double");
        }
        return Double.parseDouble(value.trim());
    }

    /**
     * Supprime le préfixe "code_" d'un code d'élément.
     *
     * @param code le code à nettoyer
     * @return le code nettoyé
     */
    public static String cleanUpElementCode(String code) {
        return code.replace("code_", "");
    }

    /**
     * Déclenche une exception métier si les mesures existent déjà pour une période donnée.
     *
     * @param exists     indique si les données existent
     * @param startDate  date de début
     * @param endDate    date de fin
     * @param typeReleve type de relevé
     * @throws UnnecessaryApiRequestException si la condition est vraie
     */
    public static void throwIfExists(boolean exists, LocalDateTime startDate, LocalDateTime endDate, TypeReleve typeReleve) throws UnnecessaryApiRequestException {
        if (exists) {
            throw new UnnecessaryApiRequestException(String.format("Forecast already exists within range %s / %s for the type %s", DateUtils.toStringCompletePattern(startDate), DateUtils.toStringCompletePattern(endDate), typeReleve));
        }
    }

    /**
     * Déclenche une exception métier si les mesures existent déjà pour une journée donnée.
     *
     * @param exists     si des mesures existent
     * @param startDate  date de relevé
     * @param typeReleve type de relevé
     * @throws UnnecessaryApiRequestException si la condition est vraie
     */
    public static void throwIfExists(boolean exists, LocalDateTime startDate, TypeReleve typeReleve) throws UnnecessaryApiRequestException {
        if (exists) {
            throw new UnnecessaryApiRequestException(String.format("Forecast already exists for today (%s) for the type %s", DateUtils.toStringCompletePattern(startDate), typeReleve));
        }
    }

    /**
     * Ajoute une {@link MesurePrevision} à une liste si la valeur est non nulle.
     *
     * @param mesures liste cible
     * @param valeur  valeur de la mesure
     * @param type    type de relevé
     * @param releve  date de relevé
     * @param maj     date d'enregistrement
     * @param nature  nature de la mesure
     */
    public static void addIfNotNull(List<MesurePrevision> mesures, String valeur, TypeReleve type, LocalDateTime releve, LocalDateTime maj, NatureMesurePrevision nature) {
        try {
            if (valeur != null) {
                mesures.add(createMesurePrevision(type, releve, maj, valeur, nature));
            }
        } catch (ParsedDataException e) {
            logger.debug("Couldn't convert value to double for {}, at {}", type, maj);
        }
    }

    /**
     * Crée une mesure de prévision météo.
     *
     * @param typeReleve type de relevé
     * @param dateReleve date du relevé
     * @param dateMaj    date d'enregistrement
     * @param valeur     valeur brute
     * @param nature     nature de la mesure
     * @return la mesure construite
     * @throws ParsedDataException si la valeur n'est pas convertible
     */
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

    /**
     * Définit la nature et l'unité en fonction de la nature de la mesure.
     *
     * @param mesure mesure à modifier
     * @param nature nature de la mesure
     */
    public static void setNatureAndUnite(MesurePrevision mesure, NatureMesurePrevision nature) {
        mesure.setNature(nature.toString());
        switch (nature) {
            case HUMIDITY, CLOUD_COVERAGE -> mesure.setUnite("%");
            case TEMPERATURE, TEMPERATURE_MAX, TEMPERATURE_FELT, TEMPERATURE_MIN, WIND_ORIENTATION ->
                    mesure.setUnite("°");
            case PRESSURE -> mesure.setUnite("hpa");
            case VISIBILITY -> mesure.setUnite("m");
            case WIND_SPEED, WIND_SPEED_GUST -> mesure.setUnite("m/s");
            case RAIN_1H, RAIN_3H, SNOW_1H, SNOW_3H -> mesure.setUnite("mm/h");
            default -> mesure.setUnite("tbd");
        }
    }

    /**
     * Extrait les {@link MesureAir} d'un ensemble de mesures polymorphes.
     *
     * @param mesures liste mixte
     * @return liste filtrée
     */
    public static List<MesureAir> getMesureAir(Collection<Mesure> mesures) {
        return mesures.stream()
                .filter(m -> m instanceof MesureAir)
                .map(m -> (MesureAir) m)
                .collect(Collectors.toList());
    }

    /**
     * Extrait les {@link MesurePrevision} d'un ensemble de mesures polymorphes.
     *
     * @param mesures liste mixte
     * @return liste filtrée
     */
    public static List<MesurePrevision> getMesurePrevision(Collection<Mesure> mesures) {
        return mesures.stream()
                .filter(m -> m instanceof MesurePrevision)
                .map(m -> (MesurePrevision) m)
                .collect(Collectors.toList());
    }

    /**
     * Alimente un DTO météo avec les dernières mesures de prévision.
     *
     * @param latestPrevisions liste des mesures
     * @param detailMeteo      DTO à compléter
     */
    public static void setDetailMeteo(List<MesurePrevision> latestPrevisions, DetailMeteo detailMeteo) {
        if (!latestPrevisions.isEmpty()) {
            for (MesurePrevision mp : latestPrevisions) {
                try {
                    NatureMesurePrevision meteo = NatureMesurePrevision.valueOf(mp.getNature().toUpperCase());
                    Map<Double, String> valeurUnite = Map.of(mp.getValeur(), mp.getUnite());
                    detailMeteo.addMeteo(meteo, valeurUnite);
                } catch (IllegalArgumentException e) {
                    // Mesure inconnue, on ignore
                }
            }
        }
    }

    /**
     * Alimente un DTO qualité de l'air avec les derniers indices.
     *
     * @param latestAirs mesures d'air
     * @param detailAir  DTO à compléter
     */
    public static void setDetailAir(List<MesureAir> latestAirs, DetailAir detailAir) {
        if (!latestAirs.isEmpty()) {
            for (MesureAir ma : latestAirs) {
                try {
                    String codeElement = ma.getCodeElement().toUpperCase();
                    AirPolluant polluant = AirPolluant.valueOf(codeElement);

                    if (polluant == AirPolluant.ATMO) {
                        detailAir.addIndice(polluant, ma.getIndice());
                    } else {
                        detailAir.addValue(codeElement, ma.getValeur());
                        detailAir.addUnit(codeElement, ma.getUnite());
                    }
                } catch (IllegalArgumentException ignored) {
                    if ("PM2.5".equals(ma.getCodeElement())) {
                        detailAir.addValue("PM2.5", ma.getValeur());
                        detailAir.addUnit("PM2.5", ma.getUnite());
                    }
                }
            }
        }
    }

    /**
     * Crée une mesure d'air à partir de données brutes.
     *
     * @param codeElement code du polluant
     * @param indice      indice calculé
     * @param dateReleve  date du relevé
     * @param timeStamp   date d'enregistrement
     * @return instance de {@link MesureAir}
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

    public static MesureAir createMesureAirWithValue(String codeElement, String unite, double valeur, LocalDateTime dateReleveTime, LocalDateTime timestamp) {
        MesureAir mAir = new MesureAir();
        mAir.setTypeMesure(TypeMesure.RELEVE_AIR);
        mAir.setCodeElement(codeElement);
        mAir.setUnite(unite);
        mAir.setValeur(valeur);
        mAir.setDateReleve(dateReleveTime);
        mAir.setDateEnregistrement(timestamp);
        try {
            int indice = calculateIndice(codeElement, valeur);
            mAir.setIndice(indice);
        } catch (FunctionnalException e) {
            logger.debug(e.getMessage());
            mAir.setIndice(0);
        }
        return mAir;
    }


    public static void throwIfExists(boolean exists, LocalDateTime timeStamp, LocalDateTime endDate) throws UnnecessaryApiRequestException {
        if (exists) {
            throw new UnnecessaryApiRequestException(String.format("Air Forecast already exists this hour %s", DateUtils.toStringCompletePattern(timeStamp)));
        }
    }
}
