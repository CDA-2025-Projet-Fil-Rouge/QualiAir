package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Region;
import fr.diginamic.qualiair.repository.RegionRepository;
import fr.diginamic.qualiair.validator.RegionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RegionService {

    @Autowired
    private Map<String, Region> regionCache;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private RegionValidator regionValidator;

    public Region findOrCreate(Region region) {
        if (regionCache.get(region.getNom()) != null) {
            return regionCache.get(region.getNom());
        }
        regionValidator.validate(region);
        regionRepository.save(region);
        regionCache.put(region.getNom(), region);
        return region;
    }
}
