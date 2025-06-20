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
public class RegionServiceImpl implements RegionService {

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

    @Override
    public Region findOrCreate(Region region) {

        int key = region.getCode();
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
