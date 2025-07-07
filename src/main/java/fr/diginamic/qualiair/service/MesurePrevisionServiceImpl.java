package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.mapper.MesurePrevisionMapper;
import fr.diginamic.qualiair.repository.MesurePrevisionRepository;
import fr.diginamic.qualiair.repository.MesureRepository;
import fr.diginamic.qualiair.validator.MesureValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service permettant la gestion des {@link MesurePrevision}.
 */
@Service
public class MesurePrevisionServiceImpl implements MesurePrevisionService {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(MesurePrevisionServiceImpl.class);

    @Autowired
    private MesurePrevisionRepository repository;
    @Autowired
    private MesureValidator validator;
    @Autowired
    private MesurePrevisionMapper mapper;
    @Autowired
    private MesureRepository mesureRepository;

    @Override
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

    @Override
    public boolean existsByHourAndCodeInsee(LocalDateTime startDate, LocalDateTime endDate, TypeReleve typeReleve, String codeInsee) {
        return repository.existsByCodeInseeAndTypeReleveAndDateReleveBetween(codeInsee, typeReleve, startDate, endDate);
    }

    @Override
    public boolean existsForTodayByTypeReleveAndCodeInsee(LocalDateTime dateExpiration, TypeReleve typeReleve, String codeInsee) {
        return repository.existByCodeInseeAndTypeReleveAndDate(typeReleve, codeInsee, dateExpiration);
    }

    @Override
    public HistoriquePrevision getAllByNatureAndCodeInseeBetweenDates(GeographicalScope scope, NatureMesurePrevision nature, String codeInsee, LocalDateTime dateStart, LocalDateTime dateEnd) {
        List<MesurePrevision> mesures = repository.getAllByNatureAndCoordonnee_Commune_CodeInseeBetweenDates(nature.toString(), codeInsee, dateStart, dateEnd);

        return mapper.toHistoricalDto(scope, codeInsee, nature, mesures);
    }

    @Override
    @Transactional
    public void deleteByTypeReleve(TypeReleve typeReleve) {
        List<Long> idsToDelete = repository.findIdsByTypeReleve(typeReleve);
        repository.deleteAllByTypeReleve(typeReleve);
        mesureRepository.deleteAllByIdInBatch(idsToDelete);
        logger.info("Deleted {} forecast records and their parent mesure records", idsToDelete.size());
    }

    @Override
    public HistoriquePrevision getAllByNatureAndCodeRegionBetweenDates(GeographicalScope scope, NatureMesurePrevision nature, String code, LocalDateTime dateStart, LocalDateTime dateEnd) {
        throw new UnsupportedOperationException("Not supported yet");//todo

    }

    @Override
    public HistoriquePrevision getAllByNatureAndCodeDepartementBetweenDates(GeographicalScope scope, NatureMesurePrevision nature, String code, LocalDateTime dateStart, LocalDateTime dateEnd) {
        throw new UnsupportedOperationException("Not supported yet");//todo

    }

}
