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

    public boolean existsByHourAndCodeInsee(LocalDateTime startDate, LocalDateTime endDate, TypeReleve typeReleve, String codeInsee) {
        return repository.existsByCodeInseeAndTypeReleveAndDateReleveBetween(codeInsee, typeReleve, startDate, endDate);
    }

    public boolean existsForTodayByTypeReleveAndCodeInsee(LocalDateTime timeStamp, TypeReleve typeReleve, String codeInsee) {
        return repository.existByCodeInseeAndTypeReleveAndDate(typeReleve, codeInsee, timeStamp);
    }

    public HistoriquePrevision getAllByNatureAndCodeInseeBetweenDates(NatureMesurePrevision nature, String codeInsee, LocalDate dateStart, LocalDate dateEnd) {
        List<MesurePrevision> mesures = repository.getAllByNatureAndCoordonnee_Commune_CodeInseeBetweenDates(nature, codeInsee, dateStart, dateEnd);

        return mapper.toHistoricalDto(nature, mesures);
    }

}
