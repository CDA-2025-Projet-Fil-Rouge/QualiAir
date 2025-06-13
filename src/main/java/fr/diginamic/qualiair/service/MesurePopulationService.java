package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.repository.MesurePopulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static fr.diginamic.qualiair.utils.MesureUtils.toKey;

@Service
public class MesurePopulationService {
    @Autowired
    MesurePopulationRepository mesurePopulationRepository;
    @Autowired
    private Map<String, MesurePopulation> cache;

    public MesurePopulation save(MesurePopulation mesure) {

        String key = toKey(mesure);
        if (cache.get(key) != null) {
            return cache.get(key);
        }
        mesurePopulationRepository.save(mesure);
        cache.put(key, mesure);
        return mesure;
    }
}
