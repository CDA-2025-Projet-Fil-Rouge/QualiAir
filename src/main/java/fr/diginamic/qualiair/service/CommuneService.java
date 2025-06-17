package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dao.CommuneDao;
import fr.diginamic.qualiair.dto.InfoCarteCommune;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.exception.FunctionnalException;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.mapper.CommuneMapper;
import fr.diginamic.qualiair.repository.CommuneRepository;
import fr.diginamic.qualiair.validator.CommuneValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Commune service
 */
@Service
public class CommuneService {

    /**
     * Cache service
     */
    @Autowired
    private CacheService cacheService;
    /**
     * Commune repository
     */
    @Autowired
    private CommuneRepository communeRepository;
    /**
     * Commune validator
     */
    @Autowired
    private CommuneValidator communeValidator;
    @Autowired
    private CommuneDao dao;
    @Autowired
    private CommuneMapper mapper;

    /**
     * Update a commune from its name
     *
     * @param commune commune
     */
    public void updateByName(Commune commune) {

    }

    /**
     * Find from cache or create an entity and add it to the cache
     *
     * @param commune commune entity
     * @return existing or created entity
     */
    public Commune findOrCreate(Commune commune) throws ParsedDataException {

        String key = commune.getNomPostal();
        Commune existing = cacheService.findInCommuneCache(key);

        if (existing != null) {
            return existing;
        }
        communeValidator.validate(commune);
        Commune saved = dao.save(commune);
        cacheService.putInCommuneCache(key, saved);
        return saved;
    }

    /**
     * Return a commune present in the cache from its name
     *
     * @param communeName commune name
     * @return existing commune
     */
    public Commune getFromCache(String communeName) {

        return cacheService.findInCommuneCache(communeName);
    }

    public Commune findByNomPostal(String nomPostal) throws FunctionnalException {
        Commune commune = communeRepository.findCommuneByNomPostal(nomPostal);
        if (commune == null) {
            throw new FunctionnalException("Commune not found for nomPostal: " + nomPostal);
        }
        return commune;
    }

    public List<InfoCarteCommune> getListCommunesDtoByPopulation(int nbHabitant) {
        List<Commune> communes = communeRepository.findTopByMesurePopulationWithCurrentForecast(nbHabitant);
        List<InfoCarteCommune> dto = new ArrayList<>();
        communes.forEach(commune -> dto.add(mapper.toDto(commune)));
        return dto;
    }

    public List<Commune> getListTopCommunesByPopulation(int nbHabitant) {
        return communeRepository.findTopByLastestMesurePopulation(nbHabitant);
    }
}
