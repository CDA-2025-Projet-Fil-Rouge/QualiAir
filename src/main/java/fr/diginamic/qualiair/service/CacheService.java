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

@Service
public class CacheService {
    private final Map<String, Commune> communeMap = new HashMap<>();
    private final Map<String, Region> regionMap = new HashMap<>();
    private final Map<String, Departement> departementMap = new HashMap<>();
    private final Map<String, Coordonnee> coordonneeMap = new HashMap<>();
    private final Map<String, MesurePopulation> mesurePopulationMap = new HashMap<>();

    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private CoordonneRepository coordonneRepository;
    @Autowired
    private MesurePopulationRepository mesurePopulationRepository;


    public void loadAllCaches() {
        loadExistingCommunes();
        loadExistingRegions();
        loadExistingDepartements();
        loadExistingCoordonnees();
        loadExistingMesurePopulation();
    }

    public void loadExistingCommunes() {
        List<Commune> communeList = communeRepository.findAll();
        System.out.println("cache loaded");
        communeList.forEach(commune -> communeMap.put(commune.getNom(), commune));

    }

    public void loadExistingRegions() {
        List<Region> regionList = regionRepository.findAll();
        System.out.println("cache loaded");
        regionList.forEach(region -> regionMap.put(region.getNom(), region));
    }

    public void loadExistingDepartements() {
        List<Departement> departementList = departementRepository.findAll();
        System.out.println("cache loaded");
        departementList.forEach(departement -> departementMap.put(departement.getNom(), departement));
    }

    public void loadExistingCoordonnees() {
        List<Coordonnee> coordonnees = coordonneRepository.findAll();
        coordonnees.forEach(c ->
                coordonneeMap.put(toKey(c.getLatitude(), c.getLongitude()), c)
        );
    }

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

    public void clearCaches() {
        communeMap.clear();
        regionMap.clear();
        departementMap.clear();
        mesurePopulationMap.clear();
        coordonneeMap.clear();
    }
}
