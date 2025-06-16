package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import fr.diginamic.qualiair.exception.UnnecessaryApiRequestException;
import fr.diginamic.qualiair.mapper.MesurePrevisionMapper;
import fr.diginamic.qualiair.repository.MesurePrevisionRepository;
import fr.diginamic.qualiair.validator.MesurePrevisionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Service
public class MesurePrevisionService {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(MesurePrevisionService.class);

    @Autowired
    private ApiOpenWeatherService openWeatherService;
    @Autowired
    private MesurePrevisionMapper mapper;
    @Autowired
    private MesurePrevisionRepository repository;
    @Autowired
    private MesurePrevisionValidator validator;


    public List<MesurePrevision> saveMesurePrevision(List<MesurePrevision> mesures) {

        Iterator<MesurePrevision> iterator = mesures.iterator();
        while (iterator.hasNext()) {
            MesurePrevision mesure = iterator.next();
            try {
                validator.validate(mesure);
                repository.save(mesure);
            } catch (BusinessRuleException | TokenExpiredException e) {
                logger.error(e.getMessage());
                iterator.remove();
            }
        }
        return mesures;
    }

    public boolean existsByHourAndNomPostal(LocalDateTime startDate, LocalDateTime endDate, TypeReleve typeReleve, String nomPostal) throws UnnecessaryApiRequestException {
        return repository.existsByNomPostalAndTypeMesureAndDateReleveBetween(
                nomPostal, typeReleve, startDate, endDate);
    }

}
