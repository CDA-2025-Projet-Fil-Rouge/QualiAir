package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.Departement;
import fr.diginamic.qualiair.entity.Region;
import org.springframework.transaction.annotation.Transactional;

public interface CacheService {
    /**
     * Loads entity present in base
     */
    @Transactional(readOnly = true)
    void loadExistingCommunesWithRelations();

    /**
     * Recherche dans le cache communes
     *
     * @param codeInsee code insee de la commune
     * @return commune ou null
     */
    Commune findInCommuneCache(String codeInsee);

    void putInCommuneCache(String key, Commune commune);

    /**
     * Recherche dans le cache regions
     *
     * @param key nom  de la region
     * @return region ou null
     */
    Region findInRegionCache(int key);

    void putInRegionCache(int key, Region region);

    /**
     * Recherche dans le cache departement
     *
     * @param key nom  de la region
     * @return region ou null
     */
    Departement findInDepartementCache(String key);

    void putInDepartementCache(String key, Departement departement);

    /**
     * Recherche dans le cache coordonnées
     *
     * @param key codeInsee  de la commune rattachée
     * @return coordonnées ou null
     */
    Coordonnee findInCoordoneeCache(String key);

    void putInCoordonneeCache(String key, Coordonnee coordonnee);

    /**
     * cleans up cache
     */
    void clearCaches();
}
