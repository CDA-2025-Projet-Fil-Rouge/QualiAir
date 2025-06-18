package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.exception.ExportException;
import fr.diginamic.qualiair.validator.HistoriqueValidator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

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

    public HistoriquePopulation executePopulationForCommune(String codeInsee, LocalDate dateStart, LocalDate dateEnd) {
        validator.validateParams(codeInsee, dateStart, dateEnd);
        return popService.getAllByCodeInseeBetwenDates(codeInsee, dateStart, dateEnd);
    }

    public HistoriqueAirQuality executeAirQualityForCommune(AirPolluant polluant, String codeInsee, LocalDate dateStart, LocalDate dateEnd) {
        validator.validateParams(polluant, codeInsee, dateStart, dateEnd);
        return airService.getAllByPolluantAndCodeInseeBetweenDates(polluant, codeInsee, dateStart, dateEnd);
    }

    public HistoriquePrevision executePrevisionForCommune(NatureMesurePrevision nature, String codeInsee, LocalDate dateStart, LocalDate dateEnd) {

        validator.validateParams(nature, codeInsee, dateStart, dateEnd);
        return prevService.getAllByNatureAndCodeInseeBetweenDates(nature, codeInsee, dateStart, dateEnd);
    }

    public void executePrevisionForCommuneCsv(HttpServletResponse response, NatureMesurePrevision nature, String codeInsee, LocalDate dateStart, LocalDate dateEnd) throws IOException, ExportException {
        validator.validateParams(nature, codeInsee, dateStart, dateEnd);
        HistoriquePrevision historique = executePrevisionForCommune(nature, codeInsee, dateStart, dateEnd);
        csvService.buildCsv(response, historique);
    }

    public void executeAirQualityForCommuneCsv(HttpServletResponse response, AirPolluant polluant, String codeInsee, LocalDate dateStart, LocalDate dateEnd) throws IOException, ExportException {
        validator.validateParams(polluant, codeInsee, dateStart, dateEnd);
        HistoriqueAirQuality historique = executeAirQualityForCommune(polluant, codeInsee, dateStart, dateEnd);
        csvService.buildCsv(response, historique);
    }

    public void executePopulationForCommuneCsv(HttpServletResponse response, String codeInsee, LocalDate dateStart, LocalDate dateEnd) throws IOException, ExportException {
        validator.validateParams(codeInsee, dateStart, dateEnd);
        HistoriquePopulation historique = popService.getAllByCodeInseeBetwenDates(codeInsee, dateStart, dateEnd);
        csvService.buildCsv(response, historique);
    }
}
