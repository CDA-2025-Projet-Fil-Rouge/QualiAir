package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface MesurePrevisionRepository extends JpaRepository<MesurePrevision, Long> {


    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
            "FROM MesurePrevision m " +
            "WHERE m.typeReleve = :typeReleve " +
            "AND m.coordonnee.commune.nomPostal = :nomPostal " +
            "AND m.dateReleve BETWEEN :startDate AND :endDate")
    boolean existsByNomPostalAndTypeMesureAndDateReleveBetween(@Param("nomPostal") String nomPostal, @Param("typeReleve") TypeReleve typeReleve, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
