package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Coordonnee;

public interface CoordonneeService {
    /**
     * Find from cache or create an entity and add it to the cache
     *
     * @param coordonnee commune entity
     * @return existing or created entity
     */
    Coordonnee findOrCreate(Coordonnee coordonnee);
}
