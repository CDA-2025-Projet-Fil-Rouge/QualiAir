package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.builder.CsvBuilder;
import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.enumeration.TypeExport;
import fr.diginamic.qualiair.exception.ExportException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Service dédié à la génération et à l'export CSV des différents historiques.
 * Permet de construire un fichier CSV à partir d'objets {@link HistoriquePrevision},
 * {@link HistoriqueAirQuality} ou {@link HistoriquePopulation} et de l'envoyer dans la réponse HTTP.
 */
@Service
public class CsvService {

    /**
     * Génère un CSV à partir d'un {@link HistoriquePrevision} et l'écrit dans la réponse HTTP.
     *
     * @param response   réponse HTTP dans laquelle écrire le CSV
     * @param historique données d'historique de prévision à exporter
     * @throws ExportException en cas d'erreur d'export
     * @throws IOException     en cas de problème d'écriture dans la réponse
     */
    public void buildCsv(HttpServletResponse response, HistoriquePrevision historique) throws ExportException, IOException {
        CsvBuilder builder = new CsvBuilder(response, TypeExport.HISTORIQUE_PREV);
        builder.toLines(historique).build();
    }

    /**
     * Génère un CSV à partir d'un {@link HistoriqueAirQuality} et l'écrit dans la réponse HTTP.
     *
     * @param response   réponse HTTP dans laquelle écrire le CSV
     * @param historique données d'historique de qualité de l'air à exporter
     * @throws ExportException en cas d'erreur d'export
     * @throws IOException     en cas de problème d'écriture dans la réponse
     */
    public void buildCsv(HttpServletResponse response, HistoriqueAirQuality historique) throws ExportException, IOException {
        CsvBuilder builder = new CsvBuilder(response, TypeExport.HISTORIQUE_AIR);
        builder.toLines(historique).build();
    }

    /**
     * Génère un CSV à partir d'un {@link HistoriquePopulation} et l'écrit dans la réponse HTTP.
     *
     * @param response   réponse HTTP dans laquelle écrire le CSV
     * @param historique données d'historique de population à exporter
     * @throws ExportException en cas d'erreur d'export
     * @throws IOException     en cas de problème d'écriture dans la réponse
     */
    public void buildCsv(HttpServletResponse response, HistoriquePopulation historique) throws ExportException, IOException {
        CsvBuilder builder = new CsvBuilder(response, TypeExport.HISTORIQUE_POP);
        builder.toLines(historique).build();
    }
}
