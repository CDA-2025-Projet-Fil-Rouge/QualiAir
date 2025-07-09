package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.MesurePopulation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @EntityGraph(attributePaths = {
            "mesure",
            "mesure.coordonnee",
            "mesure.coordonnee.commune",
            "mesure.coordonnee.commune.departement",
            "mesure.coordonnee.commune.departement.region"
    })
    List<MesurePopulation> findAllByMesureCodeInseeAndMesureDateReleveBetween(String codeInsee, LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query("SELECT mp FROM MesurePopulation mp " +
           "JOIN FETCH mp.mesure m " +
           "JOIN FETCH m.coordonnee c " +
           "JOIN FETCH c.commune com " +
           "JOIN FETCH com.departement d " +
           "WHERE m.dateReleve BETWEEN :startDate AND :endDate " +
           "AND d.code = :departementCode")
    List<MesurePopulation> findAllByDepartementAndDateReleveBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("departementCode") String departementCode
    );

    @Query("SELECT mp FROM MesurePopulation mp " +
           "JOIN FETCH mp.mesure m " +
           "JOIN FETCH m.coordonnee c " +
           "JOIN FETCH c.commune com " +
           "JOIN FETCH com.departement d " +
           "JOIN FETCH d.region r " +
           "WHERE m.dateReleve BETWEEN :startDate AND :endDate " +
           "AND r.code = :regionCode")
    List<MesurePopulation> findAllByRegionAndDateReleveBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("regionCode") int regionCode
    );
}
