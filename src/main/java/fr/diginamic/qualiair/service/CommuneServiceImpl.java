package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dao.CommuneDao;
import fr.diginamic.qualiair.dto.CommuneDto;
import fr.diginamic.qualiair.dto.carte.InfoCarteCommune;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.RouteParamException;
import fr.diginamic.qualiair.mapper.CommuneMapper;
import fr.diginamic.qualiair.repository.CommuneRepository;
import fr.diginamic.qualiair.validator.CommuneValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service permettant la gestion des {@link Commune}.
 */
@Service
public class CommuneServiceImpl implements CommuneService {

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

    @Override
    public void updateByName(Commune commune) {

    }

    @Override
    public Commune findOrCreate(Commune commune) {

        String key = commune.getCodeInsee();
        Commune existing = cacheService.findInCommuneCache(key);

        if (existing != null) {
            return existing;
        }
        communeValidator.validate(commune);
        Commune saved = dao.save(commune);
        cacheService.putInCommuneCache(key, saved);
        return saved;
    }

    @Override
    public Commune getFromCache(String communeName) {

        return cacheService.findInCommuneCache(communeName);
    }

    @Override
    public List<InfoCarteCommune> getListCommunesDtoByPopulation(int nbHabitant) {
        List<Long> communeIds = communeRepository.findCommuneIdsByPopulation(nbHabitant);

        List<Commune> communes = communeRepository.findWithMesuresById(communeIds);

        return communes.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<Commune> getListTopCommunesByPopulation(int nbHabitant) {
        return communeRepository.findTopByLastestMesurePopulation(nbHabitant);
    }

    @Override
    public List<Commune> getAllCommunesWithCoordinates() {
        return communeRepository.findAllWithCoordinates();
    }

    @Override
    public List<CommuneDto> matchTop10ByName(String containing) throws RouteParamException {
        if (containing.length() < 3) {
            throw new RouteParamException("Route params must contain at least 3 characters");
        }
        return null;
    }

    @Override
    public List<Commune> getAllFavoritesByUserId(Long userId) {
        return communeRepository.findAllFavoritesByUserId(userId);
    }

    @Override
    public Commune getCommuneById(Long communeId) throws DataNotFoundException {
        Commune commune = communeRepository.getCommuneById(communeId);
        if (commune == null) {
            throw new DataNotFoundException("Commune non trouvée pour l'id" + communeId);
        }
        return commune;
    }
}
