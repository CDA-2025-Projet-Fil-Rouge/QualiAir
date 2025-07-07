package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.exception.ExportException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

public interface HistoriqueService {
    /**
     * Récupère l'historique de {@link MesurePopulation} pour une {@link Commune} entre deux dates.
     *
     * @param codeInsee code INSEE de la {@link Commune}
     * @param dateStart date de début
     * @param dateEnd   date de fin
     * @return {@link HistoriquePopulation}
     */
    HistoriquePopulation executePopulationForCommune(String codeInsee, LocalDate dateStart, LocalDate dateEnd);

    /**
     * Récupère l'historique de {@link MesureAir} pour une {@link Commune} et un polluant({@link AirPolluant}) donné.
     *
     * @param polluant  polluant concerné
     * @param codeInsee code INSEE de la {@link Commune}
     * @param dateStart date de début
     * @param dateEnd   date de fin
     * @return {@link HistoriqueAirQuality}
     */
    HistoriqueAirQuality executeAirQualityForCommune(AirPolluant polluant, String codeInsee, LocalDate dateStart, LocalDate dateEnd);

    /**
     * Récupère l'historique {@link MesurePrevision} pour une {@link Commune}, une nature de mesure et une période.
     *
     * @param nature    nature de la mesure de prévision
     * @param codeInsee code INSEE de la {@link Commune}
     * @param dateStart date de début
     * @param dateEnd   date de fin
     * @return {@link HistoriquePrevision}
     */
    HistoriquePrevision executePrevisionForCommune(NatureMesurePrevision nature, String codeInsee, LocalDate dateStart, LocalDate dateEnd);

    /**
     * Génère un export CSV de l'historique {@link MesurePrevision} pour une {@link Commune}.
     *
     * @param response  réponse HTTP où écrire le CSV
     * @param nature    nature de la mesure de prévision
     * @param codeInsee code INSEE de la {@link Commune}
     * @param dateStart date de début
     * @param dateEnd   date de fin
     * @throws IOException     en cas d'erreur d'écriture
     * @throws ExportException en cas d'erreur durant l'export
     */
    void executePrevisionForCommuneCsv(HttpServletResponse response, NatureMesurePrevision nature, String codeInsee, LocalDate dateStart, LocalDate dateEnd) throws IOException, ExportException;

    /**
     * Génère un export CSV de l'historique qualité de l'air pour une {@link Commune}.
     *
     * @param response  réponse HTTP où écrire le CSV
     * @param polluant  polluant concerné
     * @param codeInsee code INSEE de la {link Commune}
     * @param dateStart date de début
     * @param dateEnd   date de fin
     * @throws IOException     en cas d'erreur d'écriture
     * @throws ExportException en cas d'erreur durant l'export
     */
    void executeAirQualityForCommuneCsv(HttpServletResponse response, AirPolluant polluant, String codeInsee, LocalDate dateStart, LocalDate dateEnd) throws IOException, ExportException;

    /**
     * Génère un export CSV de l'historique population pour une {link Commune}.
     *
     * @param response  réponse HTTP où écrire le CSV
     * @param codeInsee code INSEE de la {link Commune}
     * @param dateStart date de début
     * @param dateEnd   date de fin
     * @throws IOException     en cas d'erreur d'écriture
     * @throws ExportException en cas d'erreur durant l'export
     */
    void executePopulationForCommuneCsv(HttpServletResponse response, String codeInsee, LocalDate dateStart, LocalDate dateEnd) throws IOException, ExportException;
}
