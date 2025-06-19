package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.mapper.MesurePrevisionMapper;
import fr.diginamic.qualiair.repository.MesurePrevisionRepository;
import fr.diginamic.qualiair.validator.MesureValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service permettant la gestion des {@link MesurePrevision}.
 */
@Service
public class MesurePrevisionService {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(MesurePrevisionService.class);

    @Autowired
    private MesurePrevisionRepository repository;
    @Autowired
    private MesureValidator validator;
    @Autowired
    private MesurePrevisionMapper mapper;

    /**
     * Valide et enregistre en base de données une liste de {@link MesurePrevision}.
     * Les mesures invalides sont ignorées avec log d'erreur. Aucune exception n'est propagée.
     *
     * @param mesures liste des mesures à sauvegarder
     * @return liste des mesures validées et enregistrées
     */
    public List<MesurePrevision> saveMesurePrevision(List<MesurePrevision> mesures) {
        List<MesurePrevision> saved = new ArrayList<>();
        for (MesurePrevision mesure : mesures) {
            try {
                validator.validate(mesure);
                saved.add(repository.save(mesure));
            } catch (BusinessRuleException e) {
                logger.error(e.getMessage());
            }
        }
        return saved;
    }

    /**
     * Vérifie si des {@link MesurePrevision} existent entre deux dates pour un type de relevé et un code INSEE donnés.
     *
     * @param startDate  date de début (incluse)
     * @param endDate    date de fin (incluse)
     * @param typeReleve type de relevé ({@link TypeReleve})
     * @param codeInsee  code INSEE de la commune
     * @return true si des mesures existent, false sinon
     */
    public boolean existsByHourAndCodeInsee(LocalDateTime startDate, LocalDateTime endDate, TypeReleve typeReleve, String codeInsee) {
        return repository.existsByCodeInseeAndTypeReleveAndDateReleveBetween(codeInsee, typeReleve, startDate, endDate);
    }

    /**
     * Vérifie si une {@link MesurePrevision} existe pour la date du jour, pour un code INSEE et un type de relevé donnés.
     *
     * @param timeStamp  date et heure du jour (utilisé pour déterminer la date cible)
     * @param typeReleve type de relevé ({@link TypeReleve})
     * @param codeInsee  code INSEE de la commune
     * @return true si une mesure existe pour aujourd’hui, false sinon
     */
    public boolean existsForTodayByTypeReleveAndCodeInsee(LocalDateTime timeStamp, TypeReleve typeReleve, String codeInsee) {
        return repository.existByCodeInseeAndTypeReleveAndDate(typeReleve, codeInsee, timeStamp);
    }

    /**
     * Récupère les {@link MesurePrevision} d'une commune entre deux dates, pour une nature spécifique,
     * puis les convertit {@link HistoriquePrevision}.
     *
     * @param nature    nature de la mesure de prévision (ex. OZONE, NO2, etc.)
     * @param codeInsee code INSEE de la commune concernée
     * @param dateStart date de début de la période
     * @param dateEnd   date de fin de la période
     * @return objet {@link HistoriquePrevision} contenant les mesures agrégées
     */
    public HistoriquePrevision getAllByNatureAndCodeInseeBetweenDates(NatureMesurePrevision nature, String codeInsee, LocalDate dateStart, LocalDate dateEnd) {
        List<MesurePrevision> mesures = repository.getAllByNatureAndCoordonnee_Commune_CodeInseeBetweenDates(nature, codeInsee, dateStart, dateEnd);

        return mapper.toHistoricalDto(nature, mesures);
    }

}
