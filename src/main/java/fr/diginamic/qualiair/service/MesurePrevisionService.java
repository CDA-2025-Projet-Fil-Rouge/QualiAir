package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
import fr.diginamic.qualiair.enumeration.GeographicalScope;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MesurePrevisionService {
    /**
     * Valide et enregistre en base de données une liste de {@link MesurePrevision}.
     * Les mesures invalides sont ignorées avec log d'erreur. Aucune exception n'est propagée.
     *
     * @param mesures liste des mesures à sauvegarder
     * @return liste des mesures validées et enregistrées
     */
    List<MesurePrevision> saveMesurePrevision(List<MesurePrevision> mesures);

    /**
     * Vérifie si des {@link MesurePrevision} existent entre deux dates pour un type de relevé et un code INSEE donnés.
     *
     * @param startDate  date de début (incluse)
     * @param endDate    date de fin (incluse)
     * @param typeReleve type de relevé ({@link TypeReleve})
     * @param codeInsee  code INSEE de la commune
     * @return true si des mesures existent, false sinon
     */
    boolean existsByHourAndCodeInsee(LocalDateTime startDate, LocalDateTime endDate, TypeReleve typeReleve, String codeInsee);

    /**
     * Vérifie si une {@link MesurePrevision} existe pour la date du jour, pour un code INSEE et un type de relevé donnés.
     *
     * @param dateExpiration date et heure du jour (utilisé pour déterminer la date cible)
     * @param typeReleve     type de relevé ({@link TypeReleve})
     * @param codeInsee      code INSEE de la commune
     * @return true si une mesure existe pour aujourd’hui, false sinon
     */
    boolean existsForTodayByTypeReleveAndCodeInsee(LocalDateTime dateExpiration, TypeReleve typeReleve, String codeInsee);

    /**
     * Récupère les {@link MesurePrevision} d'une commune entre deux dates, pour une nature spécifique,
     * puis les convertit {@link HistoriquePrevision}.
     *
     * @param nature    nature de la mesure de prévision (ex. OZONE, NO2, etc.)
     * @param codeInsee code INSEE de la commune concernée
     * @param dateStart date de début de la période
     * @param dateEnd   date de fin de la période
     * @return objet {@link HistoriquePrevision} contenant les mesures agrégées
     */
    HistoriquePrevision getAllByNatureAndCodeInseeBetweenDates(GeographicalScope scope, NatureMesurePrevision nature, String codeInsee, LocalDate dateStart, LocalDate dateEnd);

    //todo doc
    void deleteByTypeReleve(TypeReleve typeReleve);
    //todo doc

    HistoriquePrevision getAllByNatureAndCodeRegionBetweenDates(GeographicalScope scope, NatureMesurePrevision nature, String code, LocalDate dateStart, LocalDate dateEnd);
    //todo doc

    HistoriquePrevision getAllByNatureAndCodeDepartementBetweenDates(GeographicalScope scope, NatureMesurePrevision nature, String code, LocalDate dateStart, LocalDate dateEnd);
}
