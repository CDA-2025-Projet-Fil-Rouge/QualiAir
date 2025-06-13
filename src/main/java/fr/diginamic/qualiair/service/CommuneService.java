package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.repository.CommuneRepository;
import fr.diginamic.qualiair.validator.CommuneValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommuneService {

    @Autowired
    private Map<String, Commune> communeCache;
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private CommuneValidator communeValidator;

    public void updateByName(Commune commune) {

    }

    public Commune findOrCreate(Commune commune) {

        if (communeCache.get(commune.getNom()) != null) {
            return communeCache.get(commune.getNom());
        }
        communeValidator.validate(commune);
        communeRepository.save(commune);
        communeCache.put(commune.getNom(), commune);
        return commune;
    }

    public Commune getFromCache(String communeName) {
        Commune existing = communeCache.get(communeName);
//        if (communeCache.get(communeName) == null) {
//            throw new FunctionnalException("Commune doesn't exist for: " + communeName);
//        }
        return existing;
    }
}
