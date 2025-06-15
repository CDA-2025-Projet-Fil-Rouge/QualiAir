package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.repository.MesureAirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MesureAirService {
    @Autowired
    private MesureAirRepository repository;
    @Autowired
    private CacheService cacheService;

    public MesureAir save(MesureAir mesure) {
        repository.save(mesure);
        return mesure;
    }

    public boolean existsByDateReleve(LocalDate date) {

        return repository.existsMesureAirByDateReleve(date.atStartOfDay());
    }
}
