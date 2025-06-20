package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Departement;

public interface DepartementService {
    /**
     * Find from cache or create an entity and add it to the cache
     *
     * @param departement commune entity
     * @return existing or created entity
     */
    Departement findOrCreate(Departement departement);
}
