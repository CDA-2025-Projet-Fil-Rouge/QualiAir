package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Departement;
import fr.diginamic.qualiair.repository.DepartementRepository;
import fr.diginamic.qualiair.validator.DepartementValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        String key = departement.getNom();
        Departement existing = cacheService.findInDepartementCache(key);

        if (existing != null) {
            return existing;
        }
        departementValidator.validate(departement);
        departementRepository.save(departement);
        cacheService.putInDepartementCache(departement.getNom(), departement);
        return departement;
    }
}
