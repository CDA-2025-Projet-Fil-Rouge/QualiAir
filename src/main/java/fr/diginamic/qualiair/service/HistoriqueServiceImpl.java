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
import fr.diginamic.qualiair.validator.HistoriqueValidator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.function.Function;

/**
 * Service central permettant d'exécuter les requêtes d'historique sur les données
 * {@link MesurePopulation}, {@link MesureAir} and {@link MesurePrevision}.
 * <p>
 * Utilise les services spécialisés pour récupérer les données et valide les paramètres.
 * Permet aussi l'export au format CSV via le {@link CsvServiceImpl}.
 * </p>
 */
@Service
public class HistoriqueServiceImpl implements HistoriqueService {
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

    @Override
    public HistoriquePrevision executePrevision(GeographicalScope scope, String scopedCode, NatureMesurePrevision nature, LocalDate dateStart, LocalDate dateEnd) {
        return scopeResolver(scope, scopedCode, nature, dateStart, dateEnd,
                (code) -> prevService.getAllByNatureAndCodeInseeBetweenDates(scope, nature, code, dateStart, dateEnd),
                (code) -> prevService.getAllByNatureAndCodeRegionBetweenDates(scope, nature, code, dateStart, dateEnd),
                (code) -> prevService.getAllByNatureAndCodeDepartementBetweenDates(scope, nature, code, dateStart, dateEnd)
        );
    }

    @Override
    public HistoriqueAirQuality executeAirQuality(GeographicalScope scope, String scopedCode, AirPolluant polluant, LocalDate dateStart, LocalDate dateEnd) {
        return scopeResolver(scope, scopedCode, polluant, dateStart, dateEnd,
                (code) -> airService.getAllByPolluantAndCodeInseeBetweenDates(scope, code, polluant, dateStart, dateEnd),
                (code) -> airService.getAllByPolluantAndCodeRegionBetweenDates(scope, code, polluant, dateStart, dateEnd),
                (code) -> airService.getAllByPolluantAndCodeDepartementBetweenDates(scope, code, polluant, dateStart, dateEnd)
        );
    }

    @Override
    public HistoriquePopulation executePopulation(GeographicalScope scope, String scopedCode, LocalDate dateStart, LocalDate dateEnd) {
        return scopeResolver(scope, scopedCode, null, dateStart, dateEnd,
                (code) -> popService.getAllByCodeInseeBetwenDates(scope, code, dateStart, dateEnd),
                (code) -> popService.getAllByCodeRegionBetweenDates(scope, code, dateStart, dateEnd),
                (code) -> popService.getAllByCodeDepartementBetweenDates(scope, code, dateStart, dateEnd)
        );
    }

    @Override
    public void executePrevisionCsv(HttpServletResponse response, GeographicalScope scope, String scopedCode, NatureMesurePrevision nature, LocalDate dateStart, LocalDate dateEnd) throws IOException, ExportException {
        HistoriquePrevision historique = executePrevision(scope, scopedCode, nature, dateStart, dateEnd);
        csvService.buildCsv(response, historique);  // TODO handle List of scopedCode instead of a single one (to return all displayed)
    }

    @Override
    public void executeAirQualityCsv(HttpServletResponse response, GeographicalScope scope, String scopedCode, AirPolluant polluant, LocalDate dateStart, LocalDate dateEnd) throws IOException, ExportException {
        HistoriqueAirQuality historique = executeAirQuality(scope, scopedCode, polluant, dateStart, dateEnd);
        csvService.buildCsv(response, historique);  // TODO handle List of scopedCode instead of a single one (to return all displayed)
    }

    @Override
    public void executePopulationCsv(HttpServletResponse response, GeographicalScope scope, String scopedCode, LocalDate dateStart, LocalDate dateEnd) throws IOException, ExportException {
        HistoriquePopulation historique = executePopulation(scope, scopedCode, dateStart, dateEnd);
        csvService.buildCsv(response, historique);  // TODO handle List of scopedCode instead of a single one (to return all displayed)
    }

    /**
     * Résolveur générique pour les scopes géographiques
     *
     * @param scope               Le scope géographique (COMMUNE, REGION, DEPARTEMENT)
     * @param scopedCode          Le code correspondant au scope
     * @param parameter           Paramètre optionnel (nature, polluant, etc.)
     * @param dateStart           Date de début
     * @param dateEnd             Date de fin
     * @param communeFunction     Fonction pour récupérer les données au niveau commune
     * @param regionFunction      Fonction pour récupérer les données au niveau région
     * @param departementFunction Fonction pour récupérer les données au niveau département
     * @return Les données historiques selon le scope
     */
    private <T> T scopeResolver(GeographicalScope scope, String scopedCode, Object parameter,
                                LocalDate dateStart, LocalDate dateEnd,
                                Function<String, T> communeFunction,
                                Function<String, T> regionFunction,
                                Function<String, T> departementFunction) {

        if (parameter instanceof NatureMesurePrevision nature) {
            validator.validateParams(nature, scopedCode, dateStart, dateEnd);
        } else if (parameter instanceof AirPolluant polluant) {
            validator.validateParams(polluant, scopedCode, dateStart, dateEnd);
        } else {
            validator.validateParams(scopedCode, dateStart, dateEnd);
        }

        return switch (scope) {
            case COMMUNE -> communeFunction.apply(scopedCode);
            case REGION -> regionFunction.apply(scopedCode);
            case DEPARTEMENT -> departementFunction.apply(scopedCode);
        };
    }
}
