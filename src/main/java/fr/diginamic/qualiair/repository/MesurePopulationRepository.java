package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.MesurePopulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * MesurePopulation repository
 */
@Repository
public interface MesurePopulationRepository extends JpaRepository<MesurePopulation, Long> {

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MesureAir m WHERE m.mesure.dateEnregistrement >= :startOfDay AND m.mesure.dateEnregistrement < :startOfNextDay")
    boolean existsByDate(LocalDateTime startOfDay, LocalDateTime startOfNextDay);

    boolean existsMesurePopulationByMesure_DateReleve(LocalDateTime dateReleve);

    @Query("""
            SELECT m FROM MesurePopulation m
            WHERE m.mesure.coordonnee.commune.codeInsee = :codeInsee AND m.mesure.dateReleve BETWEEN :dateStart AND :dateEnd
            """)
    List<MesurePopulation> getAllByMesure_Coordonnee_Commune_CodeInseeAndMesureDateReleveBetween(String codeInsee, LocalDateTime dateStart, LocalDateTime dateEnd);
}
