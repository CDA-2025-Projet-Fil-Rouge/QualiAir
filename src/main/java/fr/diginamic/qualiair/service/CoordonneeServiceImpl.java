package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dao.CoordoneeDao;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.repository.CoordonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service permettant la gestion des {@link Coordonnee}.
 */
@Service
public class CoordonneeServiceImpl implements CoordonneeService {

    /**
     * Cache service
     */
    @Autowired
    private CacheService cacheService;
    /**
     * Coordinates repository
     */
    @Autowired
    private CoordonneRepository coordonneRepository;
    @Autowired
    private CoordoneeDao dao;

    @Override
    public Coordonnee findOrCreate(Coordonnee coordonnee) {

        String key = coordonnee.getCommune().getCodeInsee();

        Coordonnee existing = cacheService.findInCoordoneeCache(key);
        if (existing != null) {
            return existing;
        }
        Coordonnee saved = dao.save(coordonnee);
        cacheService.putInCoordonneeCache(key, saved);
        return saved;
    }
}
