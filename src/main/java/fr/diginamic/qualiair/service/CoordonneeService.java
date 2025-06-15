package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dao.CoordoneeDao;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.repository.CoordonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Cordinates service
 */
@Service
public class CoordonneeService {

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

    /**
     * Find from cache or create an entity and add it to the cache
     *
     * @param coordonnee commune entity
     * @return existing or created entity
     */
    public Coordonnee findOrCreate(Coordonnee coordonnee) throws ParsedDataException {


        String key = coordonnee.getCommune().getNomPostal();

        Coordonnee existing = cacheService.findInCoordoneeCache(key);
        if (existing != null) {
            return existing;
        }

        dao.save(coordonnee);
        cacheService.putInCoordonneeCache(key, coordonnee);
        return coordonnee;
    }
}
