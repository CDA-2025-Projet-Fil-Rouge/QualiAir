package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.repository.CommuneRepository;
import fr.diginamic.qualiair.validator.CommuneValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Commune service
 */
@Service
public class CommuneService {

    /**
     * Cache service
     */
    @Autowired
    private CacheService cacheService;
    /**
     * Commune repository
     */
    @Autowired
    private CommuneRepository communeRepository;
    /**
     * Commune validator
     */
    @Autowired
    private CommuneValidator communeValidator;

    /**
     * Update a commune from its name
     *
     * @param commune commune
     */
    public void updateByName(Commune commune) {

    }

    /**
     * Find from cache or create an entity and add it to the cache
     *
     * @param commune commune entity
     * @return existing or created entity
     */
    public Commune findOrCreate(Commune commune) {

        String key = commune.getNom();
        Commune existing = cacheService.findInCommuneCache(key);

        if (existing != null) {
            return existing;
        }
        communeValidator.validate(commune);
        communeRepository.save(commune);
        cacheService.putInCommuneCache(commune.getNom(), commune);
        return commune;
    }

    /**
     * Return a commune present in the cache from its name
     *
     * @param communeName commune name
     * @return existing commune
     */
    public Commune getFromCache(String communeName) {

        return cacheService.findInCommuneCache(communeName);
    }
}
