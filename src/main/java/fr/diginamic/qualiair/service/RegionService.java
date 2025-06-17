package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Region;
import fr.diginamic.qualiair.repository.RegionRepository;
import fr.diginamic.qualiair.validator.RegionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Region service
 */
@Service
public class RegionService {

    /**
     * cache service
     */
    @Autowired
    private CacheService cacheService;
    /**
     * region repository
     */
    @Autowired
    private RegionRepository regionRepository;
    /**
     * region validator
     */
    @Autowired
    private RegionValidator regionValidator;

    /**
     * find or create a region from the cache and add it to the cache if new
     *
     * @param region region entity
     * @return existing or new region
     */
    public Region findOrCreate(Region region) {

        String key = region.getNom();
        Region existing = cacheService.findInRegionCache(key);
        if (existing != null) {
            return existing;
        }
        regionValidator.validate(region);
        Region saved = regionRepository.save(region);
        cacheService.putInRegionCache(key, region);
        return saved;
    }
}
