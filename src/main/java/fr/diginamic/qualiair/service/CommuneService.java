package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dao.CommuneDao;
import fr.diginamic.qualiair.dto.CommuneDto;
import fr.diginamic.qualiair.dto.InfoCarteCommune;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.RouteParamException;
import fr.diginamic.qualiair.mapper.CommuneMapper;
import fr.diginamic.qualiair.repository.CommuneRepository;
import fr.diginamic.qualiair.validator.CommuneValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service permettant la gestion des {@link Commune}.
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
     * Met à jour une commune à partir de son nom.
     *
     * @param commune la commune à mettre à jour
     */
    public void updateByName(Commune commune) {

    }

    /**
     * Cherche une commune dans le cache ou la crée et l'ajoute au cache.
     *
     * @param commune la commune à chercher ou créer
     * @return la commune existante ou nouvellement créée
     */
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

    /**
     * Récupère une commune dans le cache à partir de son nom.
     *
     * @param communeName nom de la commune
     * @return la commune trouvée dans le cache, ou null si absente
     */
    public Commune getFromCache(String communeName) {

        return cacheService.findInCommuneCache(communeName);
    }

    /**
     * Récupère une liste DTO des communes avec une population minimale donnée.
     *
     * @param nbHabitant nombre d'habitants minimum
     * @return liste des DTO {@link InfoCarteCommune}
     */
    public List<InfoCarteCommune> getListCommunesDtoByPopulation(int nbHabitant) {
        List<Commune> communes = communeRepository.findTopByMesurePopulationWithCurrentForecastWithAllReleveRelations(nbHabitant);
        List<InfoCarteCommune> dto = new ArrayList<>();
        communes.forEach(commune -> dto.add(mapper.toDto(commune)));
        return dto;
    }

    /**
     * Récupère une liste des communes avec la plus récente mesure de population,
     * filtrée par population minimale.
     *
     * @param nbHabitant nombre d'habitants minimum
     * @return liste des communes
     */
    public List<Commune> getListTopCommunesByPopulation(int nbHabitant) {
        return communeRepository.findTopByLastestMesurePopulation(nbHabitant);
    }

    /**
     * Récupère toutes les communes qui ont des coordonnées.
     *
     * @return liste des communes
     */
    public List<Commune> getAllCommunesWithCoordinates() {
        return communeRepository.findAllWithCoordinates();
    }

    /**
     * Recherche jusqu'à 10 communes dont le nom contient la chaîne donnée.
     *
     * @param containing chaîne à chercher (min. 3 caractères)
     * @return liste des DTO {@link CommuneDto}
     * @throws RouteParamException si la chaîne est trop courte
     */
    public List<CommuneDto> matchTop10ByName(String containing) throws RouteParamException {
        if (containing.length() < 3) {
            throw new RouteParamException("Route params must contain at least 3 characters");
        }
        return null;
    }

    /**
     * Récupère toutes les communes favorites d'un utilisateur.
     *
     * @param userId identifiant de l'utilisateur
     * @return liste des communes favorites
     */
    public List<Commune> getAllFavoritesByUserId(Long userId) {
        return communeRepository.findAllFavoritesByUserId(userId);
    }

    /**
     * Récupère une commune par son identifiant.
     *
     * @param communeId identifiant de la commune
     * @return la commune trouvée
     * @throws DataNotFoundException si aucune commune ne correspond à l'id
     */
    public Commune getCommuneById(Long communeId) throws DataNotFoundException {
        Commune commune = communeRepository.getCommuneById(communeId);
        if (commune == null) {
            throw new DataNotFoundException("Commune non trouvée pour l'id" + communeId);
        }
        return commune;
    }
}
