package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.Departement;
import fr.diginamic.qualiair.repository.DepartementRepository;
import fr.diginamic.qualiair.validator.DepartementValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DepartementService {

    @Autowired
    private CacheService cacheService;
    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private DepartementValidator departementValidator;

    public Departement findOrCreate(Departement departement) {
        Map<String, Departement> departementCache = cacheService.getDepartementMap();

        if (departementCache.get(departement.getNom()) != null) {
            return departementCache.get(departement.getNom());
        }
        departementValidator.validate(departement);
        departementRepository.save(departement);
        departementCache.put(departement.getNom(), departement);
        return departement;
    }
}
