package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
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
            AND m.coordonnee.commune.codeInsee = :codeInsee
            AND m.dateReleve BETWEEN :startDate AND :endDate""")
    boolean existsByCodeInseeAndTypeReleveAndDateReleveBetween(@Param("codeInsee") String codeInsee, @Param("typeReleve") TypeReleve typeReleve, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("""
            SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END
            FROM MesurePrevision m
            WHERE m.typeReleve = :typeReleve
            AND m.coordonnee.commune.codeInsee = :codeInsee
            AND m.dateEnregistrement >= :dateExpiration""")
    boolean existByCodeInseeAndTypeReleveAndDate(@Param("typeReleve") TypeReleve typeReleve, @Param("codeInsee") String codeInsee, @Param("dateExpiration") LocalDateTime dateExpiration);


    @Query("""
            SELECT m FROM MesurePrevision m
            WHERE m.coordonnee.commune.codeInsee = :codeInsee AND m.nature = :nature AND m.dateReleve BETWEEN :dateStart AND :dateEnd
            """)
    List<MesurePrevision> getAllByNatureAndCoordonnee_Commune_CodeInseeBetweenDates(String nature, String codeInsee, LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query("SELECT mp.id FROM MesurePrevision mp WHERE mp.typeReleve = :typeReleve")
    List<Long> findIdsByTypeReleve(@Param("typeReleve") TypeReleve typeReleve);


    @Modifying
    void deleteAllByTypeReleve(TypeReleve typeReleve);
}
