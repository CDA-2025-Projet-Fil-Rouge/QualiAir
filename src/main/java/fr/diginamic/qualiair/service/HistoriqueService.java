package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
import fr.diginamic.qualiair.exception.ExportException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Service pour la gestion des données historiques avec support des différents scopes géographiques.
 * <p>
 * Ce service permet de récupérer les données historiques pour les mesures de prévision,
 * la qualité de l'air et la population selon différents niveaux géographiques
 * (commune, région, département).
 * </p>
 */
public interface HistoriqueService {

    /**
     * Récupère l'historique de {@link MesurePrevision} selon le scope géographique spécifié.
     *
     * @param scope      le scope géographique (COMMUNE, REGION, DEPARTEMENT)
     * @param scopedCode le code correspondant au scope (code INSEE, code région, code département)
     * @param nature     la nature de la mesure de prévision
     * @param dateStart  la date de début de la période
     * @param dateEnd    la date de fin de la période
     * @return {@link HistoriquePrevision} contenant les données historiques
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    HistoriquePrevision executePrevision(GeographicalScope scope, String scopedCode,
                                         NatureMesurePrevision nature, LocalDate dateStart, LocalDate dateEnd);

    /**
     * Récupère l'historique de {@link MesureAir} selon le scope géographique spécifié.
     *
     * @param scope      le scope géographique (COMMUNE, REGION, DEPARTEMENT)
     * @param scopedCode le code correspondant au scope (code INSEE, code région, code département)
     * @param polluant   le polluant concerné
     * @param dateStart  la date de début de la période
     * @param dateEnd    la date de fin de la période
     * @return {@link HistoriqueAirQuality} contenant les données historiques de qualité de l'air
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    HistoriqueAirQuality executeAirQuality(GeographicalScope scope, String scopedCode,
                                           AirPolluant polluant, LocalDate dateStart, LocalDate dateEnd);

    /**
     * Récupère l'historique de {@link MesurePopulation} selon le scope géographique spécifié.
     *
     * @param scope      le scope géographique (COMMUNE, REGION, DEPARTEMENT)
     * @param scopedCode le code correspondant au scope (code INSEE, code région, code département)
     * @param dateStart  la date de début de la période
     * @param dateEnd    la date de fin de la période
     * @return {@link HistoriquePopulation} contenant les données historiques de population
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    HistoriquePopulation executePopulation(GeographicalScope scope, String scopedCode,
                                           LocalDate dateStart, LocalDate dateEnd);

    /**
     * Génère un export CSV de l'historique de {@link MesurePrevision} selon le scope géographique.
     *
     * @param response   la réponse HTTP où écrire le fichier CSV
     * @param scope      le scope géographique (COMMUNE, REGION, DEPARTEMENT)
     * @param scopedCode le code correspondant au scope (code INSEE, code région, code département)
     * @param nature     la nature de la mesure de prévision
     * @param dateStart  la date de début de la période
     * @param dateEnd    la date de fin de la période
     * @throws IOException              en cas d'erreur d'écriture dans la réponse HTTP
     * @throws ExportException          en cas d'erreur durant la génération du CSV
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    void executePrevisionCsv(HttpServletResponse response, GeographicalScope scope, String scopedCode,
                             NatureMesurePrevision nature, LocalDate dateStart, LocalDate dateEnd)
            throws IOException, ExportException;

    /**
     * Génère un export CSV de l'historique de {@link MesureAir} selon le scope géographique.
     *
     * @param response   la réponse HTTP où écrire le fichier CSV
     * @param scope      le scope géographique (COMMUNE, REGION, DEPARTEMENT)
     * @param scopedCode le code correspondant au scope (code INSEE, code région, code département)
     * @param polluant   le polluant concerné
     * @param dateStart  la date de début de la période
     * @param dateEnd    la date de fin de la période
     * @throws IOException              en cas d'erreur d'écriture dans la réponse HTTP
     * @throws ExportException          en cas d'erreur durant la génération du CSV
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    void executeAirQualityCsv(HttpServletResponse response, GeographicalScope scope, String scopedCode,
                              AirPolluant polluant, LocalDate dateStart, LocalDate dateEnd)
            throws IOException, ExportException;

    /**
     * Génère un export CSV de l'historique de {@link MesurePopulation} selon le scope géographique.
     *
     * @param response   la réponse HTTP où écrire le fichier CSV
     * @param scope      le scope géographique (COMMUNE, REGION, DEPARTEMENT)
     * @param scopedCode le code correspondant au scope (code INSEE, code région, code département)
     * @param dateStart  la date de début de la période
     * @param dateEnd    la date de fin de la période
     * @throws IOException              en cas d'erreur d'écriture dans la réponse HTTP
     * @throws ExportException          en cas d'erreur durant la génération du CSV
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    void executePopulationCsv(HttpServletResponse response, GeographicalScope scope, String scopedCode,
                              LocalDate dateStart, LocalDate dateEnd)
            throws IOException, ExportException;
}
