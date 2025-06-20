package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Region;

public interface RegionService {
    /**
     * find or create a region from the cache and add it to the cache if new
     *
     * @param region region entity
     * @return existing or new region
     */
    Region findOrCreate(Region region);
}
