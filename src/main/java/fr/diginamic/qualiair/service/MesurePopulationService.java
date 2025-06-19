package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.mapper.MesurePopulationMapper;
import fr.diginamic.qualiair.repository.MesurePopulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Service permettant la gestion des {@link MesurePopulation}.
 */
@Service
public class MesurePopulationService {

    @Autowired
    private MesurePopulationRepository repository;

    @Autowired
    private MesurePopulationMapper mapper;

    /**
     * Enregistre une {@link MesurePopulation} en base de données.
     *
     * @param mesurePopulation la mesure à sauvegarder
     * @return l'entité sauvegardée
     */
    public MesurePopulation save(MesurePopulation mesurePopulation) {
        return repository.save(mesurePopulation);
    }

    /**
     * Enregistre une liste de {@link MesurePopulation} en base de données.
     *
     * @param mesures liste des mesures à sauvegarder
     */
    public void saveAll(List<MesurePopulation> mesures) {
        repository.saveAll(mesures);
    }

    /**
     * Recherche si un relevé de {@link MesurePopulation} existe déjà à cette date afin d'éviter une requete externe inutile.
     * Cette méthode regarde si des données sont présentes entre le début de la journée
     * et le début du jour suivant.
     *
     * @param mesureDate date cible
     * @return true si des mesures existent à cette date, false sinon
     */
    public boolean existsByDate(LocalDate mesureDate) {

        LocalDateTime startOfDay = mesureDate.atStartOfDay();
        LocalDateTime startOfNextDay = mesureDate.plusDays(1).atStartOfDay();

        return repository.existsByDate(startOfDay, startOfNextDay);
    }

    /**
     * Recherche si un relevé de {@link MesurePopulation} existe déjà à cette date afin d'éviter une requete externe inutile.
     *
     * @param dateReleve date cible du relevé
     * @return true si des mesures existent déjà
     */
    public boolean existByDateReleve(LocalDate dateReleve) {
        return repository.existsMesurePopulationByDateReleve(dateReleve.atStartOfDay());
    }

    /**
     * Recupère toutes les {@link MesurePopulation} d'une commune sur un période donnée et effectue la conversion en {@link HistoriquePopulation} dto.
     *
     * @param codeInsee code insee de la commune
     * @param dateStart date début
     * @param dateEnd   date de fin
     * @return dto {@link HistoriquePopulation}
     */
    public HistoriquePopulation getAllByCodeInseeBetwenDates(String codeInsee, LocalDate dateStart, LocalDate dateEnd) {
        List<MesurePopulation> mesures = repository.getAllByNatureAndCoordonnee_Commune_CodeInseeBetweenDates(codeInsee, dateStart, dateEnd);

        return mapper.toHistoricalDto(mesures);
    }
}
