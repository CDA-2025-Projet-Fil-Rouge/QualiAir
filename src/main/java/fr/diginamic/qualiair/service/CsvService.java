package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.exception.ExportException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface CsvService {
    /**
     * Génère un CSV à partir d'un {@link HistoriquePrevision} et l'écrit dans la réponse HTTP.
     *
     * @param response   réponse HTTP dans laquelle écrire le CSV
     * @param historique données d'historique de prévision à exporter
     * @throws ExportException en cas d'erreur d'export
     * @throws IOException     en cas de problème d'écriture dans la réponse
     */
    void buildCsv(HttpServletResponse response, HistoriquePrevision historique) throws ExportException, IOException;

    /**
     * Génère un CSV à partir d'un {@link HistoriqueAirQuality} et l'écrit dans la réponse HTTP.
     *
     * @param response   réponse HTTP dans laquelle écrire le CSV
     * @param historique données d'historique de qualité de l'air à exporter
     * @throws ExportException en cas d'erreur d'export
     * @throws IOException     en cas de problème d'écriture dans la réponse
     */
    void buildCsv(HttpServletResponse response, HistoriqueAirQuality historique) throws ExportException, IOException;

    /**
     * Génère un CSV à partir d'un {@link HistoriquePopulation} et l'écrit dans la réponse HTTP.
     *
     * @param response   réponse HTTP dans laquelle écrire le CSV
     * @param historique données d'historique de population à exporter
     * @throws ExportException en cas d'erreur d'export
     * @throws IOException     en cas de problème d'écriture dans la réponse
     */
    void buildCsv(HttpServletResponse response, HistoriquePopulation historique) throws ExportException, IOException;
}
