package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MesurePrevisionRepository extends JpaRepository<MesurePrevision, Long> {


    @Query("""
            SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END
            FROM MesurePrevision m
            WHERE m.typeReleve = :typeReleve
            AND m.coordonnee.commune.codeInsee = :codeInsee
            AND m.dateReleve BETWEEN :startDate AND :endDate""")
    boolean existsByCodeInseeAndTypeMesureAndDateReleveBetween(String codeInsee, TypeReleve typeReleve, LocalDateTime startDate, LocalDateTime endDate);

}
