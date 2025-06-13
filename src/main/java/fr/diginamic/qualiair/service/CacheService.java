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
    private final Map<String, Commune> communeMap = new HashMap<>();
    /**
     * Region name - region entity map
     */
    private final Map<String, Region> regionMap = new HashMap<>();
    /**
     * Departement name - department entity map
     */
    private final Map<String, Departement> departementMap = new HashMap<>();
    /**
     * coordinates key - coordinates entity map
     */
    private final Map<String, Coordonnee> coordonneeMap = new HashMap<>();
    /**
     * Mesures key - mesure entity map
     */
    private final Map<String, MesurePopulation> mesurePopulationMap = new HashMap<>();

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
        communeList.forEach(commune -> communeMap.put(commune.getNom(), commune));

    }

    /**
     * Loads entity present in base
     */
    public void loadExistingRegions() {
        List<Region> regionList = regionRepository.findAll();
        System.out.println("cache loaded");
        regionList.forEach(region -> regionMap.put(region.getNom(), region));
    }

    /**
     * Loads entity present in base
     */
    public void loadExistingDepartements() {
        List<Departement> departementList = departementRepository.findAll();
        System.out.println("cache loaded");
        departementList.forEach(departement -> departementMap.put(departement.getNom(), departement));
    }

    /**
     * Loads entity present in base
     */
    public void loadExistingCoordonnees() {
        List<Coordonnee> coordonnees = coordonneRepository.findAll();
        coordonnees.forEach(c ->
                coordonneeMap.put(toKey(c.getLatitude(), c.getLongitude()), c)
        );
    }

    /**
     * Loads entity present in base
     */
    public void loadExistingMesurePopulation() {
        List<MesurePopulation> mesuresPopulation = mesurePopulationRepository.findAll();
        mesuresPopulation.forEach(m -> mesurePopulationMap.put(MesureUtils.toKey(m), m));
    }

    /**
     * Getter
     *
     * @return communeMap
     */
    public Map<String, Commune> getCommuneMap() {
        return communeMap;
    }

    /**
     * Getter
     *
     * @return regionMap
     */
    public Map<String, Region> getRegionMap() {
        return regionMap;
    }

    /**
     * Getter
     *
     * @return departementMap
     */
    public Map<String, Departement> getDepartementMap() {
        return departementMap;
    }

    /**
     * Getter
     *
     * @return coordonneeMap
     */
    public Map<String, Coordonnee> getCoordonneeMap() {
        return coordonneeMap;
    }

    /**
     * Getter
     *
     * @return mesurePopulationMap
     */
    public Map<String, MesurePopulation> getMesurePopulationMap() {
        return mesurePopulationMap;
    }

    /**
     * cleans up cache
     */
    public void clearCaches() {
        communeMap.clear();
        regionMap.clear();
        departementMap.clear();
        mesurePopulationMap.clear();
        coordonneeMap.clear();
    }
}
