package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.*;
import fr.diginamic.qualiair.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.diginamic.qualiair.utils.CoordonneeUtils.toKey;

@Configuration
@Profile("commandLineApp")
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

    @Bean
    @Profile("commandLineApp")
    public Map<String, Commune> loadExistingCommunes() {
        List<Commune> communeList = communeRepository.findAll();
        System.out.println("cache loaded");
        communeList.forEach(commune -> communeMap.put(commune.getNom(), commune));
        return communeMap;
    }

    @Bean
    @Profile("commandLineApp")
    public Map<String, Region> loadExistingRegions() {
        List<Region> regionList = regionRepository.findAll();
        System.out.println("cache loaded");
        regionList.forEach(region -> regionMap.put(region.getNom(), region));
        return regionMap;
    }

    @Bean
    @Profile("commandLineApp")
    public Map<String, Departement> loadExistingDepartements() {
        List<Departement> departementList = departementRepository.findAll();
        System.out.println("cache loaded");
        departementList.forEach(departement -> departementMap.put(departement.getNom(), departement));
        return departementMap;
    }

    @Bean
    @Profile("commandLineApp")
    public Map<String, Coordonnee> loadExistingCoordonnees() {
        List<Coordonnee> coordonnees = coordonneRepository.findAll();
        coordonnees.forEach(c ->
                coordonneeMap.put(toKey(c.getLatitude(), c.getLongitude()), c)
        );
        return coordonneeMap;
    }

    @Bean
    @Profile("commandLineApp")
    public Map<String, MesurePopulation> loadExistingMesurePopulation() {
        List<MesurePopulation> mesuresPopulation = mesurePopulationRepository.findAll();
        mesuresPopulation.forEach(m -> mesurePopulationMap.put(m.getNom(), m));
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
