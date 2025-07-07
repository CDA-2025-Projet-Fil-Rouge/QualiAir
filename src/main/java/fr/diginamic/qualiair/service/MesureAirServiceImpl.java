package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
import fr.diginamic.qualiair.mapper.MesureAirMapper;
import fr.diginamic.qualiair.repository.MesureAirRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Service permettant la gestion des {@link MesureAir}.
 */
@Service
public class MesureAirServiceImpl implements MesureAirService {
    private static final Logger logger = LoggerFactory.getLogger(MesureAirServiceImpl.class);
    @Autowired
    private MesureAirRepository repository;
    @Autowired
    private MesureAirMapper mapper;

    @Override
    public MesureAir save(MesureAir mesure) {
        return repository.save(mesure);
    }

    @Override
    public boolean existsByDateReleve(LocalDate date) {

        return repository.existsMesureAirByDateReleve(date.atStartOfDay());
    }


    @Override
    public Page<MesureAir> findWithDetailsByTypeAndIndiceLessThan(AirPolluant polluant, int maxIndice, Pageable pageable) {
        return repository.findWithDetailsByTypeAndIndiceLessThan(polluant.toString(), maxIndice, pageable);
    }

    @Override
    @Transactional
    public List<MesureAir> saveMesureList(List<MesureAir> mesures) {
        List<MesureAir> saved = new ArrayList<>();
        for (MesureAir mesure : mesures) {
//            try{
//                validator.validate(mesure);
            saved.add(repository.save(mesure));
//            } catch (BusinessRuleException e){
//                logger.error(e.getMessage());
//            }
        }
        return saved;
    }

    @Override
    public boolean existsByHour(String codeInsee, LocalDateTime timeStamp, LocalDateTime endDate) {
        LocalDateTime start = timeStamp.truncatedTo(ChronoUnit.HOURS);
        LocalDateTime end = timeStamp.truncatedTo(ChronoUnit.HOURS).plusHours(1).minusNanos(1);
        return repository.existsMesureAirByCodeInseeAndDateReleveBetween(codeInsee, start, end);
    }

    @Override
    public HistoriqueAirQuality getAllByPolluantAndCodeInseeBetweenDates(GeographicalScope scope, String codeInsee, AirPolluant polluant, LocalDateTime dateStart, LocalDateTime dateEnd) {
        String elem = polluant.toString();
        List<MesureAir> mesures;
        if (elem.equalsIgnoreCase("pm2.5") || elem.equalsIgnoreCase("pm25")) {
            mesures = repository.getAllByPolluantAndCoordonnee_Commune_CodeInseeBetweenDates("PM2.5", codeInsee, dateStart, dateEnd);
        } else {
            mesures = repository.getAllByPolluantAndCoordonnee_Commune_CodeInseeBetweenDates(polluant.toString(), codeInsee, dateStart, dateEnd);
        }

        return mapper.toHistoriqueDto(scope, codeInsee, polluant, mesures);
    }

    @Override
    public HistoriqueAirQuality getAllByPolluantAndCodeRegionBetweenDates(GeographicalScope scope, String codeRegion, AirPolluant polluant, LocalDateTime dateStart, LocalDateTime dateEnd) {
        throw new UnsupportedOperationException("Not supported yet");//todo

    }

    @Override
    public HistoriqueAirQuality getAllByPolluantAndCodeDepartementBetweenDates(GeographicalScope scope, String codeDept, AirPolluant polluant, LocalDateTime dateStart, LocalDateTime dateEnd) {
        throw new UnsupportedOperationException("Not supported yet");//todo

    }


}
