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
public class CacheServiceImpl implements CacheService {
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


    @Transactional(readOnly = true)
    @Override
    public void loadExistingCommunesWithRelations() {
        List<Commune> communeList = communeRepository.findAllWithRelations();
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

    @Override
    public Commune findInCommuneCache(String codeInsee) {
        return communeCache.get(codeInsee);
    }

    @Override
    public void putInCommuneCache(String key, Commune commune) {
        communeCache.put(key, commune);
    }

    @Override
    public Region findInRegionCache(int key) {
        return regionCache.get(key);
    }

    @Override
    public void putInRegionCache(int key, Region region) {
        regionCache.put(key, region);
    }

    @Override
    public Departement findInDepartementCache(String key) {
        return departementCache.get(key);
    }

    @Override
    public void putInDepartementCache(String key, Departement departement) {
        departementCache.put(key, departement);
    }

    @Override
    public Coordonnee findInCoordoneeCache(String key) {
        return coordonneeCache.get(key);
    }

    @Override
    public void putInCoordonneeCache(String key, Coordonnee coordonnee) {
        coordonneeCache.put(key, coordonnee);
    }


    @Override
    public void clearCaches() {
        communeCache.clear();
        regionCache.clear();
        departementCache.clear();
        coordonneeCache.clear();
    }
}
