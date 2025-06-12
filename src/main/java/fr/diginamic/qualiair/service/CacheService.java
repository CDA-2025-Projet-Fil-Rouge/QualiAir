package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.entity.Departement;
import fr.diginamic.qualiair.entity.Region;
import fr.diginamic.qualiair.repository.CommuneRepository;
import fr.diginamic.qualiair.repository.DepartementRepository;
import fr.diginamic.qualiair.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@Profile("commandLineApp")
public class CacheService {
    private final Map<String, Commune> communeMap = new HashMap<>();
    private final Map<String, Region> regionMap = new HashMap<>();
    private final Map<String, Departement> departementMap = new HashMap<>();

    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private RegionRepository regionRepository;

    @Bean
    @Profile("commandLineApp")
    public Map<String, Commune> loadExistingCommunes() {
        List<Commune> communeList = communeRepository.findAll();

        communeList.forEach(commune -> communeMap.put(commune.getNom(), commune));
        return communeMap;
    }

    @Bean
    @Profile("commandLineApp")
    public Map<String, Region> loadExistingRegions() {
        List<Region> regionList = regionRepository.findAll();

        regionList.forEach(region -> regionMap.put(region.getNom(), region));
        return regionMap;
    }

    @Bean
    @Profile("commandLineApp")
    public Map<String, Departement> loadExistingDepartements() {
        List<Departement> departementList = departementRepository.findAll();

        departementList.forEach(departement -> departementMap.put(departement.getNom(), departement));
        return departementMap;
    }

    public void clearCaches() {
        communeMap.clear();
        regionMap.clear();
        departementMap.clear();
    }
}
