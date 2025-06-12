package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.exception.FunctionnalException;
import fr.diginamic.qualiair.repository.CommuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommuneService {

    @Autowired
    private Map<String, Commune> communeCache;
    @Autowired
    private CommuneRepository communeRepository;

    public void updateByName(Commune commune) {
    }


    public void findOrCreate(Commune commune) {

        if (communeCache.get(commune.getNom()) == null) {
            throw new FunctionnalException("La commune existe déjà en base : " + commune.getNom());
        }
        communeRepository.save(commune);
    }
}
