package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.entity.MesurePopulation;

import java.time.LocalDate;
import java.util.List;

public interface MesurePopulationService {
    /**
     * Enregistre une {@link MesurePopulation} en base de données.
     *
     * @param mesurePopulation la mesure à sauvegarder
     * @return l'entité sauvegardée
     */
    MesurePopulation save(MesurePopulation mesurePopulation);

    /**
     * Enregistre une liste de {@link MesurePopulation} en base de données.
     *
     * @param mesures liste des mesures à sauvegarder
     */
    void saveAll(List<MesurePopulation> mesures);

    /**
     * Recherche si un relevé de {@link MesurePopulation} existe déjà à cette date afin d'éviter une requete externe inutile.
     * Cette méthode regarde si des données sont présentes entre le début de la journée
     * et le début du jour suivant.
     *
     * @param mesureDate date cible
     * @return true si des mesures existent à cette date, false sinon
     */
    boolean existsByDate(LocalDate mesureDate);

    /**
     * Recherche si un relevé de {@link MesurePopulation} existe déjà à cette date afin d'éviter une requete externe inutile.
     *
     * @param dateReleve date cible du relevé
     * @return true si des mesures existent déjà
     */
    boolean existByDateReleve(LocalDate dateReleve);

    /**
     * Recupère toutes les {@link MesurePopulation} d'une commune sur un période donnée et effectue la conversion en {@link HistoriquePopulation} dto.
     *
     * @param codeInsee code insee de la commune
     * @param dateStart date début
     * @param dateEnd   date de fin
     * @return dto {@link HistoriquePopulation}
     */
    HistoriquePopulation getAllByCodeInseeBetwenDates(String codeInsee, LocalDate dateStart, LocalDate dateEnd);
}
