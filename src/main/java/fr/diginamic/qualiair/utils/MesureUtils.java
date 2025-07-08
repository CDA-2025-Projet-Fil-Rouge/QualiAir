package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.annotation.DoNotInstanciate;
import fr.diginamic.qualiair.dto.carte.DetailAir;
import fr.diginamic.qualiair.dto.carte.DetailMeteo;
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
     * Extrait les {@link MesureAir} d'un ensemble de mesures polymorphes.
     *
     * @param mesures liste mixte
     * @return liste filtrée
     */
    public static List<MesureAir> getMesureAir(Collection<Mesure> mesures) {
        return mesures.stream()
                .flatMap(m -> m.getMesuresAir().stream())
                .toList();
    }

    /**
     * Extrait les {@link MesurePrevision} d'un ensemble de mesures polymorphes.
     *
     * @param mesures liste mixte
     * @return liste filtrée
     */
    public static List<MesurePrevision> getMesurePrevision(Collection<Mesure> mesures) {
        return mesures.stream()
                .flatMap(m -> m.getMesuresPrev().stream())
                .toList();
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


    public static void throwIfExists(boolean exists, LocalDateTime timeStamp, LocalDateTime endDate) throws UnnecessaryApiRequestException {
        if (exists) {
            throw new UnnecessaryApiRequestException(String.format("Air Forecast already exists this hour %s", DateUtils.toStringCompletePattern(timeStamp)));
        }
    }
}
