package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.repository.MesurePopulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * MesurePopulation Service
 */
@Service
public class MesurePopulationService {
    /**
     * MesurePopulation Repository
     */
    @Autowired
    private MesurePopulationRepository mesurePopulationRepository;
    /**
     * CacheService
     */
    @Autowired
    private CacheService cacheService;

//    /**
//     * Find from cache or create an entity and add it to the cache
//     *
//     * @param mesure commune entity
//     * @return existing or created entity
//     */
//    public MesurePopulation findOrCreate(MesurePopulation mesure) {
//
//        String key = mesure.getCoordonnee().getCommune().getNomComplet();
//        MesurePopulation existing = cacheService.findInMesurePopCache(key);
//        if (existing != null) {
//            return existing;
//        }
//        mesurePopulationRepository.save(mesure);
//        cacheService.putInMesurePopCache(key, mesure);
//        return mesure;
//    }

    public void save(MesurePopulation mesurePopulation) {
        mesurePopulationRepository.save(mesurePopulation);
    }

    public void saveAll(List<MesurePopulation> mesures) {
        mesurePopulationRepository.saveAll(mesures);
    }

    public boolean existsByDate(LocalDate mesureDate) {

        LocalDateTime startOfDay = mesureDate.atStartOfDay();
        LocalDateTime startOfNextDay = mesureDate.plusDays(1).atStartOfDay();

        return mesurePopulationRepository.existsByDate(startOfDay, startOfNextDay);
    }

    public boolean existByDateReleve(LocalDate dateReleve) {
        return mesurePopulationRepository.existsMesurePopulationByDateReleve(dateReleve.atStartOfDay());
    }
}
