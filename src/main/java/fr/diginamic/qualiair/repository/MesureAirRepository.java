package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MesureAirRepository extends JpaRepository<MesureAir, Long> {


    boolean existsMesureAirByDateReleve(LocalDateTime dateReleve);

    @Query("""
            SELECT m FROM MesureAir m
            WHERE m.coordonnee.commune.codeInsee = :codeInsee AND m.codeElement = :nature AND m.dateReleve BETWEEN :dateStart AND :dateEnd
            """)
    List<MesureAir> getAllByPolluantAndCoordonnee_Commune_CodeInseeBetweenDates(AirPolluant polluant, String codeInsee, LocalDate dateStart, LocalDate dateEnd);

    @EntityGraph(attributePaths = {
            "coordonnee",
            "coordonnee.commune",
            "coordonnee.commune.departement",
            "coordonnee.commune.departement.region"
    })
    @Query("SELECT m FROM MesureAir m WHERE m.codeElement = :polluant AND m.indice > :maxIndice ORDER BY m.dateEnregistrement DESC")
    Page<MesureAir> findWithDetailsByTypeAndIndiceLessThan(@Param("polluant") AirPolluant polluant, @Param("maxIndice") int maxIndice, Pageable pageable);

    boolean findByDateReleveBetween(LocalDateTime dateReleveAfter, LocalDateTime dateReleveBefore);
}
