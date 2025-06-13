package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Departement;
import fr.diginamic.qualiair.repository.DepartementRepository;
import fr.diginamic.qualiair.validator.DepartementValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * DepartementService
 */
@Service
public class DepartementService {

    /**
     * Cache service
     */
    @Autowired
    private CacheService cacheService;
    /**
     * Departement repository
     */
    @Autowired
    private DepartementRepository departementRepository;
    /**
     * Departement validator
     */
    @Autowired
    private DepartementValidator departementValidator;

    /**
     * Find from cache or create an entity and add it to the cache
     *
     * @param departement commune entity
     * @return existing or created entity
     */
    public Departement findOrCreate(Departement departement) {
        Map<String, Departement> departementCache = cacheService.getDepartementMap();

        if (departementCache.get(departement.getNom()) != null) {
            return departementCache.get(departement.getNom());
        }
        departementValidator.validate(departement);
        departementRepository.save(departement);
        departementCache.put(departement.getNom(), departement);
        return departement;
    }
}
