package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.repository.MesurePopulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static fr.diginamic.qualiair.utils.MesureUtils.toKey;

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

    /**
     * Find from cache or create an entity and add it to the cache
     *
     * @param mesure commune entity
     * @return existing or created entity
     */
    public MesurePopulation save(MesurePopulation mesure) {
        Map<String, MesurePopulation> cache = cacheService.getMesurePopulationMap();
        String key = toKey(mesure);
        if (cache.get(key) != null) {
            return cache.get(key);
        }
        mesurePopulationRepository.save(mesure);
        cache.put(key, mesure);
        return mesure;
    }
}
