package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.repository.CoordonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static fr.diginamic.qualiair.utils.CoordonneeUtils.toKey;

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

    /**
     * Find from cache or create an entity and add it to the cache
     *
     * @param coordonnee commune entity
     * @return existing or created entity
     */
    public Coordonnee findOrCreate(Coordonnee coordonnee) {

        String key = toKey(coordonnee.getLatitude(), coordonnee.getLongitude());

        Coordonnee existing = cacheService.findInCoordoneeCache(key);
        if (existing != null) {
            return existing;
        }

        cacheService.putInCoordonneeCache(key, coordonnee);
        coordonneRepository.save(coordonnee);
        return coordonnee;
    }
}
