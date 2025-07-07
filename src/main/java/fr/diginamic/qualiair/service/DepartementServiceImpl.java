package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dao.DepartementDao;
import fr.diginamic.qualiair.entity.Departement;
import fr.diginamic.qualiair.repository.DepartementRepository;
import fr.diginamic.qualiair.validator.IDepartementValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service permettant la gestion des {@link Departement}.
 */
@Service
public class DepartementServiceImpl implements DepartementService {

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
    private IDepartementValidator departementValidator;
    @Autowired
    private DepartementDao departementDao;

    @Override
    public Departement findOrCreate(Departement departement) {

        String key = departement.getCode();
        Departement existing = cacheService.findInDepartementCache(key);

        if (existing != null) {
            return existing;
        }
        departementValidator.validate(departement);
        Departement saved = departementDao.save(departement);
        cacheService.putInDepartementCache(key, saved);
        return saved;
    }
}
