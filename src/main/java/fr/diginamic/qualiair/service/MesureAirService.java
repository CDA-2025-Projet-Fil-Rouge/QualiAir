package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface MesureAirService {
    /**
     * Enregistre une {@link MesureAir} en base de données.
     *
     * @param mesure la mesure à sauvegarder
     * @return l'entité sauvegardée
     */
    MesureAir save(MesureAir mesure);

    /**
     * Recherche si un relevé de {@link MesureAir} existe déjà à cette date afin d'éviter une requete externe inutile.
     *
     * @param date date cible
     * @return true si des mesures existent à cette date, false sinon
     */
    boolean existsByDateReleve(LocalDate date);

    /**
     * Recupère toutes les {@link MesureAir} d'une commune sur un période donnée et effectue la conversion en {@link HistoriqueAirQuality} dto.
     *
     * @param polluant  le polluant mesuré
     * @param codeInsee code insee de la commune
     * @param dateStart date début
     * @param dateEnd   date de fin
     * @return dto {@link HistoriquePopulation}
     */
    HistoriqueAirQuality getAllByPolluantAndCodeInseeBetweenDates(AirPolluant polluant, String codeInsee, LocalDate dateStart, LocalDate dateEnd);

    /**
     * Recupère toutes les {@link MesureAir} pour un polluant donné ayant un indice supérieur à la variable maxIndice. La requete est paginée afin de limiter l'impact en base.
     *
     * @param polluant  le polluant mesuré
     * @param maxIndice le seuil critique atteint par la mesure
     * @param pageable  le format de pagination
     * @return une sous-liste de {@link MesureAir}
     */
    Page<MesureAir> findWithDetailsByTypeAndIndiceLessThan(AirPolluant polluant, int maxIndice, Pageable pageable);
}
