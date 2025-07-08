package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.MesureAir;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MesureAirRepository extends JpaRepository<MesureAir, Long> {


    boolean existsMesureAirByMesure_DateReleve(LocalDateTime dateReleve);

    @Query("""
            SELECT m FROM MesureAir m
            WHERE m.mesure.coordonnee.commune.codeInsee = :codeInsee AND m.codeElement = :polluant AND m.mesure.dateReleve BETWEEN :dateStart AND :dateEnd
            """)
    List<MesureAir> getAllByPolluantAndCoordonnee_Commune_CodeInseeBetweenDates(@Param("polluant") String polluant, @Param("codeInsee") String codeInsee, @Param("dateStart") LocalDateTime dateStart, @Param("dateEnd") LocalDateTime dateEnd);

    @EntityGraph(attributePaths = {
            "coordonnee",
            "coordonnee.commune",
            "coordonnee.commune.departement",
            "coordonnee.commune.departement.region"
    })
    @Query("SELECT m FROM MesureAir m WHERE m.codeElement = :polluant AND m.indice > :maxIndice ORDER BY m.mesure.dateEnregistrement DESC")
    Page<MesureAir> findWithDetailsByTypeAndIndiceLessThan(@Param("polluant") String polluant, @Param("maxIndice") int maxIndice, Pageable pageable);


    @Query("""
            SELECT CASE WHEN COUNT(mair) > 0 THEN true ELSE false END
            FROM MesureAir mair
            WHERE mair.mesure.coordonnee.commune.codeInsee = :codeInsee
            AND mair.mesure.dateReleve BETWEEN :startDate AND :endDate""")
    boolean existsMesureAirByCodeInseeAndDateReleveBetween(@Param("codeInsee") String codeInsee, @Param("startDate") LocalDateTime dateReleveAfter, @Param("endDate") LocalDateTime dateReleveBefore);
}
