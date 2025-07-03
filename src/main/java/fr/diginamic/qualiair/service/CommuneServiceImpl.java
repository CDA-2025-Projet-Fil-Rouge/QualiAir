package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dao.CommuneDao;
import fr.diginamic.qualiair.dto.CommuneDto;
import fr.diginamic.qualiair.dto.carte.FiveDaysForecastView;
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
        // TODO: Implement this method
        throw new UnsupportedOperationException("Method not implemented yet");
    }

    @Override
    public Commune findOrCreate(Commune commune) {
        String cacheKey = commune.getCodeInsee();
        Commune existing = cacheService.findInCommuneCache(cacheKey);
        if (existing != null) {
            return existing;
        }

        communeValidator.validate(commune);
        Commune saved = dao.save(commune);
        cacheService.putInCommuneCache(cacheKey, saved);

        return saved;
    }

    @Override
    public Commune getFromCache(String cacheKey) {
        return cacheService.findInCommuneCache(cacheKey);
    }

    @Override
    public List<InfoCarteCommune> getListCommunesDtoByPopulation(int nbHabitant) {
        List<Long> communeIds = communeRepository.findCommuneIdsByPopulation(nbHabitant);
        List<Commune> communes = communeRepository.findWithMesuresById(communeIds);

        return communes.stream()
                .map(mapper::toMapDataView)
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
        if (containing == null || containing.length() < 3) {
            throw new RouteParamException("Search term must contain at least 3 characters");
        }
        // TODO: Implement the actual search logic
        throw new UnsupportedOperationException("not supported yet");
//        return communeRepository.findTop10ByNomSimpleContainingIgnoreCase(containing)
//                .stream()
//                .map(mapper::toDto)
//                .toList();
    }

    @Override
    public List<Commune> getAllFavoritesByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return communeRepository.findAllFavoritesByUserId(userId);
    }

    @Override
    public Commune getCommuneById(Long communeId) throws DataNotFoundException {
        if (communeId == null) {
            throw new IllegalArgumentException("Commune ID cannot be null");
        }

        Commune commune = communeRepository.getCommuneById(communeId);
        if (commune == null) {
            throw new DataNotFoundException("Commune not found for ID: " + communeId);
        }
        return commune;
    }

    @Override
    public InfoCarteCommune getCommuneDtoByCodeInsee(String codeInsee) throws DataNotFoundException {
        Commune commune = findCommuneByCodeInseeWithRelations(codeInsee);
        return mapper.toMapDataView(commune);
    }

    @Override
    public FiveDaysForecastView getCommuneForecastByCodeInsee(String codeInsee) throws DataNotFoundException {
        Commune commune = findCommuneByCodeInseeWithRelations(codeInsee);
        return mapper.toForecastView(commune);
    }


    private Commune findCommuneByCodeInseeWithRelations(String codeInsee) throws DataNotFoundException {
        if (codeInsee == null || codeInsee.trim().isEmpty()) {
            throw new IllegalArgumentException("Code INSEE cannot be null or empty");
        }

        Long communeId = communeRepository.findCommuneIdByCodeInsee(codeInsee);
        if (communeId == null) {
            throw new DataNotFoundException("Commune not found for code INSEE: " + codeInsee);
        }

        Commune commune = communeRepository.findWithMesuresById(communeId);
        if (commune == null) {
            throw new DataNotFoundException("Commune data not found for code INSEE: " + codeInsee);
        }
        return commune;
    }
}
