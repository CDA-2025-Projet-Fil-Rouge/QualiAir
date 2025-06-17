package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.Departement;
import fr.diginamic.qualiair.entity.Region;
import fr.diginamic.qualiair.repository.CommuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads entities in a local cache. Meant to be used during costly insertion of all cities in the database
 */
@Service
public class CacheService {
    /**
     * Commune code Insee - commune entity map
     */
    private final Map<String, Commune> communeCache = new HashMap<>();
    /**
     * Region code - region entity map
     */
    private final Map<Integer, Region> regionCache = new HashMap<>();
    /**
     * Departement code - department entity map
     */
    private final Map<String, Departement> departementCache = new HashMap<>();
    /**
     * Code Insee commune - coordinates entity map
     */
    private final Map<String, Coordonnee> coordonneeCache = new HashMap<>();


    /**
     * commune repository
     */
    @Autowired
    private CommuneRepository communeRepository;


    /**
     * Loads entity present in base
     */
    @Transactional(readOnly = true)
    public void loadExistingCommunesWithRelations() {
        List<Commune> communeList = communeRepository.findAllWithRelations();
        System.out.println("cache loaded");
        communeList.forEach(commune -> {
            Coordonnee coordonnee = commune.getCoordonnee();
            Departement departement = commune.getDepartement();
            Region region = commune.getDepartement().getRegion();

            communeCache.put(commune.getCodeInsee(), commune);
            regionCache.put(region.getCode(), region);
            departementCache.put(departement.getCode(), departement);
            coordonneeCache.put(commune.getCodeInsee(), coordonnee);
        });

    }

    /**
     * Recherche dans le cache communes
     *
     * @param codeInsee code insee de la commune
     * @return commune ou null
     */
    public Commune findInCommuneCache(String codeInsee) {
        return communeCache.get(codeInsee);
    }

    public void putInCommuneCache(String key, Commune commune) {
        communeCache.put(key, commune);
    }

    /**
     * Recherche dans le cache regions
     *
     * @param key nom  de la region
     * @return region ou null
     */
    public Region findInRegionCache(int key) {
        return regionCache.get(key);
    }

    public void putInRegionCache(int key, Region region) {
        regionCache.put(key, region);
    }

    /**
     * Recherche dans le cache departement
     *
     * @param key nom  de la region
     * @return region ou null
     */
    public Departement findInDepartementCache(String key) {
        return departementCache.get(key);
    }

    public void putInDepartementCache(String key, Departement departement) {
        departementCache.put(key, departement);
    }

    /**
     * Recherche dans le cache coordonnées
     *
     * @param key codeInsee  de la commune rattachée
     * @return coordonnées ou null
     */
    public Coordonnee findInCoordoneeCache(String key) {
        return coordonneeCache.get(key);
    }

    public void putInCoordonneeCache(String key, Coordonnee coordonnee) {
        coordonneeCache.put(key, coordonnee);
    }


    /**
     * cleans up cache
     */
    public void clearCaches() {
        communeCache.clear();
        regionCache.clear();
        departementCache.clear();
        coordonneeCache.clear();
    }
}
