package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.repository.*;
import fr.diginamic.qualiair.utils.MesureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.diginamic.qualiair.utils.CoordonneeUtils.toKey;

/**
 * Loads entities in a local cache. Meant to be used during costly insertion of all cities in the database
 */
@Service
public class CacheService {
    /**
     * Commune name - commune entity map
     */
    private final Map<String, Commune> communeCache = new HashMap<>();
    /**
     * Region name - region entity map
     */
    private final Map<String, Region> regionCache = new HashMap<>();
    /**
     * Departement name - department entity map
     */
    private final Map<String, Departement> departementCache = new HashMap<>();
    /**
     * coordinates key - coordinates entity map
     */
    private final Map<String, Coordonnee> coordonneeCache = new HashMap<>();
    /**
     * Mesures key - mesure entity map
     */
    private final Map<String, MesurePopulation> mesurePopCache = new HashMap<>();

    /**
     * commune repository
     */
    @Autowired
    private CommuneRepository communeRepository;
    /**
     * Departement repository
     */
    @Autowired
    private DepartementRepository departementRepository;
    /**
     * Region repository
     */
    @Autowired
    private RegionRepository regionRepository;
    /**
     * Coordonnees repository
     */
    @Autowired
    private CoordonneRepository coordonneRepository;
    /**
     * Mesure repository
     */
    @Autowired
    private MesurePopulationRepository mesurePopulationRepository;

    /**
     * Loads all caches for insertion process
     */
    public void loadAllCaches() {
        loadExistingCommunes();
        loadExistingRegions();
        loadExistingDepartements();
        loadExistingCoordonnees();
        loadExistingMesurePopulation();
    }

    /**
     * Loads entity present in base
     */
    public void loadExistingCommunes() {
        List<Commune> communeList = communeRepository.findAll();
        System.out.println("cache loaded");
        communeList.forEach(commune -> communeCache.put(commune.getNom(), commune));

    }

    /**
     * Loads entity present in base
     */
    public void loadExistingRegions() {
        List<Region> regionList = regionRepository.findAll();
        System.out.println("cache loaded");
        regionList.forEach(region -> regionCache.put(region.getNom(), region));
    }

    /**
     * Loads entity present in base
     */
    public void loadExistingDepartements() {
        List<Departement> departementList = departementRepository.findAll();
        System.out.println("cache loaded");
        departementList.forEach(departement -> departementCache.put(departement.getNom(), departement));
    }

    /**
     * Loads entity present in base
     */
    public void loadExistingCoordonnees() {
        List<Coordonnee> coordonnees = coordonneRepository.findAll();
        coordonnees.forEach(c ->
                coordonneeCache.put(toKey(c.getLatitude(), c.getLongitude()), c)
        );
    }

    /**
     * Loads entity present in base
     */
    public void loadExistingMesurePopulation() {
        List<MesurePopulation> mesuresPopulation = mesurePopulationRepository.findAll();
        mesuresPopulation.forEach(m -> mesurePopCache.put(MesureUtils.toKey(m), m));
    }

    /**
     * Getter
     *
     * @return communeMap
     */
    public Map<String, Commune> getCommuneCache() {
        return communeCache;
    }

    public Commune findInCommuneCache(String key) {
        return communeCache.get(key);
    }

    public void putInCommuneCache(String key, Commune commune) {
        communeCache.put(key, commune);
    }

    /**
     * Getter
     *
     * @return regionMap
     */
    public Map<String, Region> getRegionCache() {
        return regionCache;
    }

    public Region findInRegionCache(String key) {
        return regionCache.get(key);
    }

    public void putInRegionCache(String key, Region region) {
        regionCache.put(key, region);
    }

    /**
     * Getter
     *
     * @return departementMap
     */
    public Map<String, Departement> getDepartementCache() {
        return departementCache;
    }

    public Departement findInDepartementCache(String key) {
        return departementCache.get(key);
    }

    public void putInDepartementCache(String key, Departement departement) {
        departementCache.put(key, departement);
    }

    /**
     * Getter
     *
     * @return coordonneeMap
     */
    public Map<String, Coordonnee> getCoordonneeCache() {
        return coordonneeCache;
    }

    public Coordonnee findInCoordoneeCache(String key) {
        return coordonneeCache.get(key);
    }

    public void putInCoordonneeCache(String key, Coordonnee coordonnee) {
        coordonneeCache.put(key, coordonnee);
    }

    /**
     * Getter
     *
     * @return mesurePopulationMap
     */
    public Map<String, MesurePopulation> getMesurePopCache() {
        return mesurePopCache;
    }

    public MesurePopulation findInMesurePopCache(String key) {
        return mesurePopCache.get(key);
    }

    public void putInMesurePopCache(String key, MesurePopulation mesurePop) {
        mesurePopCache.put(key, mesurePop);
    }

    /**
     * cleans up cache
     */
    public void clearCaches() {
        communeCache.clear();
        regionCache.clear();
        departementCache.clear();
        mesurePopCache.clear();
        coordonneeCache.clear();
    }

    public Mesure findInMesureCache(String key) {
        return null; //todo impl if needed
    }

    public void putInMesureCache(String key, Mesure mesure) {
        //todo impl if needed
    }
}
