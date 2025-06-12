package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.repository.CoordonneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static fr.diginamic.qualiair.utils.CoordonneeUtils.toKey;

@Service
public class CoordonneeService {

    @Autowired
    private Map<String, Coordonnee> cache;
    @Autowired
    private CoordonneRepository coordonneRepository;

    public Coordonnee findOrCreate(Coordonnee coordonnee) {
        String key = toKey(coordonnee.getLatitude(), coordonnee.getLongitude());

        if (cache.get(key) != null) {
            return cache.get(key);
        }

        cache.put(key, coordonnee);
        coordonneRepository.save(coordonnee);
        return coordonnee;
    }
}
