package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.repository.CoordonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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
        Map<String, Coordonnee> cache = cacheService.getCoordonneeMap();

        String key = toKey(coordonnee.getLatitude(), coordonnee.getLongitude());

        if (cache.get(key) != null) {
            return cache.get(key);
        }

        cache.put(key, coordonnee);
        coordonneRepository.save(coordonnee);
        return coordonnee;
    }
}
