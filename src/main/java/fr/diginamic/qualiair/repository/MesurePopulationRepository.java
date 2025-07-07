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

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MesureAir m WHERE m.dateEnregistrement >= :startOfDay AND m.dateEnregistrement < :startOfNextDay")
    boolean existsByDate(LocalDateTime startOfDay, LocalDateTime startOfNextDay);

    boolean existsMesurePopulationByDateReleve(LocalDateTime dateReleve);

    @Query("""
            SELECT m FROM MesurePrevision m
            WHERE m.coordonnee.commune.codeInsee = :codeInsee AND m.dateReleve BETWEEN :dateStart AND :dateEnd
            """)
    List<MesurePopulation> getAllByNatureAndCoordonnee_Commune_CodeInseeBetweenDates(String codeInsee, LocalDateTime dateStart, LocalDateTime dateEnd);
}
