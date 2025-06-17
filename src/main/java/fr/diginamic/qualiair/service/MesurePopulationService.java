package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.repository.MesurePopulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * MesurePopulation Service
 */
@Service
public class MesurePopulationService {
    /**
     * MesurePopulation Repository
     */
    @Autowired
    private MesurePopulationRepository mesurePopulationRepository;

    public MesurePopulation save(MesurePopulation mesurePopulation) {
        return mesurePopulationRepository.save(mesurePopulation);
    }

    public void saveAll(List<MesurePopulation> mesures) {
        mesurePopulationRepository.saveAll(mesures);
    }

    public boolean existsByDate(LocalDate mesureDate) {

        LocalDateTime startOfDay = mesureDate.atStartOfDay();
        LocalDateTime startOfNextDay = mesureDate.plusDays(1).atStartOfDay();

        return mesurePopulationRepository.existsByDate(startOfDay, startOfNextDay);
    }

    /**
     * Recherche si un relevé existe déjà à cette date afin d'éviter une requete externe inutile. Retourne true si un relevé existe déjà
     *
     * @param dateReleve date cible du relevé
     * @return boolean
     */
    public boolean existByDateReleve(LocalDate dateReleve) {
        return mesurePopulationRepository.existsMesurePopulationByDateReleve(dateReleve.atStartOfDay());
    }
}
