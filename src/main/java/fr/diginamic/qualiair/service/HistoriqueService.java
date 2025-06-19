package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.exception.ExportException;
import fr.diginamic.qualiair.validator.HistoriqueValidator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Service central permettant d'exécuter les requêtes d'historique sur les données
 * {@link MesurePopulation}, {@link MesureAir} and {@link MesurePrevision}.
 * <p>
 * Utilise les services spécialisés pour récupérer les données et valide les paramètres.
 * Permet aussi l'export au format CSV via le {@link CsvService}.
 * </p>
 */
@Service
public class HistoriqueService {
    @Autowired
    private HistoriqueValidator validator;
    @Autowired
    private MesurePrevisionService prevService;
    @Autowired
    private MesurePopulationService popService;
    @Autowired
    private MesureAirService airService;
    @Autowired
    private CsvService csvService;

    /**
     * Récupère l'historique de {@link MesurePopulation} pour une {@link Commune} entre deux dates.
     *
     * @param codeInsee code INSEE de la {@link Commune}
     * @param dateStart date de début
     * @param dateEnd   date de fin
     * @return {@link HistoriquePopulation}
     */
    public HistoriquePopulation executePopulationForCommune(String codeInsee, LocalDate dateStart, LocalDate dateEnd) {
        validator.validateParams(codeInsee, dateStart, dateEnd);
        return popService.getAllByCodeInseeBetwenDates(codeInsee, dateStart, dateEnd);
    }

    /**
     * Récupère l'historique de {@link MesureAir} pour une {@link Commune} et un polluant({@link AirPolluant}) donné.
     *
     * @param polluant  polluant concerné
     * @param codeInsee code INSEE de la {@link Commune}
     * @param dateStart date de début
     * @param dateEnd   date de fin
     * @return {@link HistoriqueAirQuality}
     */
    public HistoriqueAirQuality executeAirQualityForCommune(AirPolluant polluant, String codeInsee, LocalDate dateStart, LocalDate dateEnd) {
        validator.validateParams(polluant, codeInsee, dateStart, dateEnd);
        return airService.getAllByPolluantAndCodeInseeBetweenDates(polluant, codeInsee, dateStart, dateEnd);
    }

    /**
     * Récupère l'historique {@link MesurePrevision} pour une {@link Commune}, une nature de mesure et une période.
     *
     * @param nature    nature de la mesure de prévision
     * @param codeInsee code INSEE de la {@link Commune}
     * @param dateStart date de début
     * @param dateEnd   date de fin
     * @return {@link HistoriquePrevision}
     */
    public HistoriquePrevision executePrevisionForCommune(NatureMesurePrevision nature, String codeInsee, LocalDate dateStart, LocalDate dateEnd) {

        validator.validateParams(nature, codeInsee, dateStart, dateEnd);
        return prevService.getAllByNatureAndCodeInseeBetweenDates(nature, codeInsee, dateStart, dateEnd);
    }

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
    public void executePrevisionForCommuneCsv(HttpServletResponse response, NatureMesurePrevision nature, String codeInsee, LocalDate dateStart, LocalDate dateEnd) throws IOException, ExportException {
        validator.validateParams(nature, codeInsee, dateStart, dateEnd);
        HistoriquePrevision historique = executePrevisionForCommune(nature, codeInsee, dateStart, dateEnd);
        csvService.buildCsv(response, historique);
    }

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
    public void executeAirQualityForCommuneCsv(HttpServletResponse response, AirPolluant polluant, String codeInsee, LocalDate dateStart, LocalDate dateEnd) throws IOException, ExportException {
        validator.validateParams(polluant, codeInsee, dateStart, dateEnd);
        HistoriqueAirQuality historique = executeAirQualityForCommune(polluant, codeInsee, dateStart, dateEnd);
        csvService.buildCsv(response, historique);
    }

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
    public void executePopulationForCommuneCsv(HttpServletResponse response, String codeInsee, LocalDate dateStart, LocalDate dateEnd) throws IOException, ExportException {
        validator.validateParams(codeInsee, dateStart, dateEnd);
        HistoriquePopulation historique = popService.getAllByCodeInseeBetwenDates(codeInsee, dateStart, dateEnd);
        csvService.buildCsv(response, historique);
    }
}
