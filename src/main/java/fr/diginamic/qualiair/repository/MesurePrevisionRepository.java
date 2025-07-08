package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MesurePrevisionRepository extends JpaRepository<MesurePrevision, Long> {


    @Query("""
            SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END
            FROM MesurePrevision m
            WHERE m.typeReleve = :typeReleve
            AND m.mesure.coordonnee.commune.codeInsee = :codeInsee
            AND m.mesure.dateReleve BETWEEN :startDate AND :endDate""")
    boolean existsByCodeInseeAndTypeReleveAndDateReleveBetween(@Param("codeInsee") String codeInsee, @Param("typeReleve") TypeReleve typeReleve, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("""
            SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END
            FROM MesurePrevision m
            WHERE m.typeReleve = :typeReleve
            AND m.mesure.coordonnee.commune.codeInsee = :codeInsee
            AND m.mesure.dateEnregistrement >= :dateExpiration""")
    boolean existByCodeInseeAndTypeReleveAndDate(@Param("typeReleve") TypeReleve typeReleve, @Param("codeInsee") String codeInsee, @Param("dateExpiration") LocalDateTime dateExpiration);


    @Query("""
            SELECT m FROM MesurePrevision m
            WHERE m.mesure.coordonnee.commune.codeInsee = :codeInsee AND m.nature = :nature AND m.mesure.dateReleve BETWEEN :dateStart AND :dateEnd
            """)
    @EntityGraph(attributePaths = {
            "mesure",
            "mesure.coordonnee",
            "mesure.coordonnee.commune",
            "mesure.coordonnee.commune.departement",
            "mesure.coordonnee.commune.departement.region"
    })
    List<MesurePrevision> getAllByNatureAndCoordonnee_Commune_CodeInseeBetweenDates(String nature, String codeInsee, LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query("""
                SELECT m FROM MesurePrevision m
                JOIN FETCH m.mesure mes
                JOIN FETCH mes.coordonnee coord
                JOIN FETCH coord.commune com
                JOIN FETCH com.departement dep
                JOIN FETCH dep.region reg
                WHERE m.nature = :nature
                AND m.typeReleve = 'ACTUEL'
                AND m.mesure.dateReleve BETWEEN :datePrevisionBefore AND :datePrevisionAfter
                AND m.mesure.coordonnee.commune.departement.code = :codeDepartement
            """)
    List<MesurePrevision> getAllByNatureAndDepartementAndDateReleveBetween(
            @Param("nature") String nature,
            @Param("datePrevisionBefore") LocalDateTime datePrevisionBefore,
            @Param("datePrevisionAfter") LocalDateTime datePrevisionAfter,
            @Param("codeDepartement") String codeDepartement
    );

    @Query("""
                SELECT m FROM MesurePrevision m
                JOIN FETCH m.mesure mes
                JOIN FETCH mes.coordonnee coord
                JOIN FETCH coord.commune com
                JOIN FETCH com.departement dep
                JOIN FETCH dep.region reg
                WHERE m.nature = :nature
                AND m.typeReleve = 'ACTUEL'
                AND m.mesure.dateReleve BETWEEN :datePrevisionBefore AND :datePrevisionAfter
                AND m.mesure.coordonnee.commune.departement.region.code = :codeRegion
            """)
    List<MesurePrevision> getAllByNatureAndMRegionCodeAndDateReleveBetween(
            @Param("nature") String nature,
            @Param("datePrevisionBefore") LocalDateTime datePrevisionBefore,
            @Param("datePrevisionAfter") LocalDateTime datePrevisionAfter,
            @Param("codeRegion") int codeRegion
    );

    @Query("SELECT mp.id FROM MesurePrevision mp WHERE mp.typeReleve = :typeReleve")
    List<Long> findIdsByTypeReleve(@Param("typeReleve") TypeReleve typeReleve);


    @Modifying
    void deleteAllByTypeReleve(TypeReleve typeReleve);
}
