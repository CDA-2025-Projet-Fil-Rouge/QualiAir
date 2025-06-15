package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Mesure;
import fr.diginamic.qualiair.repository.MesureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MesureService {

    /**
     * MesurePopulation Repository
     */
    @Autowired
    private MesureRepository mesureRepository;
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
    public Mesure save(Mesure mesure) {
        mesureRepository.save(mesure);
        return mesure;
    }

//    public Mesure findOrCreate(Mesure mesure) {
//
//        String key = toKey(mesure);
//        Mesure existing = cacheService.findInMesureCache(key);
//        if (existing != null) {
//            return existing;
//        }
//        mesureRepository.save(mesure);
//        cacheService.putInMesureCache(key, mesure);
//        return mesure;
//    }
}
