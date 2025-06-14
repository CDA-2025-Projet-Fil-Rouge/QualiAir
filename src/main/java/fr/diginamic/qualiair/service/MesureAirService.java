package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.repository.MesureAirRepository;
import fr.diginamic.qualiair.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public boolean existsByDate(String date) {
        LocalDate localDate = DateUtils.toLocalDate(date);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        LocalDateTime startOfNextDay = localDate.plusDays(1).atStartOfDay();

        return repository.existsByDate(startOfDay, startOfNextDay);
    }
}
