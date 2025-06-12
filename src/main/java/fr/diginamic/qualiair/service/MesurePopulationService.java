package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.repository.MesurePopulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MesurePopulationService {
    @Autowired
    MesurePopulationRepository mesurePopulationRepository;
    @Autowired
    private Map<String, MesurePopulation> cache;

    public MesurePopulation save(MesurePopulation mesure) {
        if (cache.get(mesure.getNom()) != null) {
            return cache.get(mesure.getNom());
        }
        mesurePopulationRepository.save(mesure);
        cache.put(mesure.getNom(), mesure);
        return mesure;
    }
}
