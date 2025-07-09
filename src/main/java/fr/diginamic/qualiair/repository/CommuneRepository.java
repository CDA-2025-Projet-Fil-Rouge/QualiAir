package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Commune repository
 */
@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {


    @Query("SELECT c FROM Commune c JOIN FETCH c.coordonnee JOIN FETCH c.departement d JOIN FETCH d.region")
    List<Commune> findAllWithRelations();

    @Query("""
            SELECT DISTINCT c FROM Commune c
            JOIN FETCH c.coordonnee cd
            JOIN FETCH cd.mesures m
            JOIN FETCH m.mesuresPop mp
            LEFT JOIN FETCH c.departement d
            LEFT JOIN FETCH d.region r
            WHERE m.typeMesure = 'RELEVE_POPULATION'
            AND mp.valeur >= :nbHab
            AND mp.mesure.dateReleve = (
                SELECT MAX(mp2.mesure.dateReleve)
                FROM MesurePopulation mp2
                WHERE mp2.mesure.coordonnee = cd
            )
            """)
    List<Commune> findTopByLastestMesurePopulation(@Param("nbHab") int nbHab);


    @Query("SELECT c FROM Commune c JOIN FETCH c.coordonnee")
    List<Commune> findAllWithCoordinates();


    @Query(value = """
                        SELECT c.favCommunes FROM Utilisateur c
                        WHERE c.id = :userId
            """)
    List<Commune> findAllFavoritesByUserId(@Param("userId") Long userId);

    Commune getCommuneById(Long id);

    Optional<Commune> findByNomReelAndCodePostal(String nomPostal, String codePostal);

    @Query("""
            SELECT c.id FROM Commune c
            WHERE c.codeInsee NOT LIKE '97%'
              AND EXISTS (
                  SELECT 1 FROM MesurePopulation mp
                  JOIN mp.mesure m
                  WHERE m.coordonnee = c.coordonnee
                    AND m.typeMesure = 'RELEVE_POPULATION'
                    AND mp.valeur >= :nbHab
                    AND mp.mesure.dateReleve = (
                        SELECT MAX(mp2.mesure.dateReleve)
                        FROM MesurePopulation mp2
                        WHERE mp2.mesure.coordonnee = c.coordonnee
                    )
              )
            """)
    List<Long> findCommuneIdsByPopulation(@Param("nbHab") int nbHab);

    @Query("""
            SELECT DISTINCT c FROM Commune c
            JOIN FETCH c.coordonnee cd
            JOIN FETCH cd.mesures m
            LEFT JOIN FETCH m.mesuresAir ma
            LEFT JOIN FETCH m.mesuresPrev mp
            LEFT JOIN FETCH m.mesuresPop mpop
            LEFT JOIN FETCH c.departement d
            LEFT JOIN FETCH d.region r
            WHERE c.id IN :ids
              AND c.codeInsee NOT LIKE '97%'
              AND (
                  (m.typeMesure = 'RELEVE_AIR' AND m.dateReleve = (
                      SELECT MAX(m2.dateReleve)
                      FROM Mesure m2
                      WHERE m2.coordonnee = cd
                        AND m2.typeMesure = 'RELEVE_AIR'
                  ))
                  OR
                  (m.typeMesure = 'RELEVE_METEO' AND EXISTS (
                      SELECT 1 FROM m.mesuresPrev mp2
                      WHERE mp2.typeReleve = 'ACTUEL'
                  ) AND m.dateReleve = (
                      SELECT MAX(m3.dateReleve)
                      FROM Mesure m3
                      JOIN m3.mesuresPrev mp3
                      WHERE m3.coordonnee = cd
                        AND m3.typeMesure = 'RELEVE_METEO'
                        AND mp3.typeReleve = 'ACTUEL'
                  ))
                  OR
                  (m.typeMesure = 'RELEVE_POPULATION' AND m.dateReleve = (
                      SELECT MAX(m4.dateReleve)
                      FROM Mesure m4
                      WHERE m4.coordonnee = cd
                        AND m4.typeMesure = 'RELEVE_POPULATION'
                  ))
              )
            """)
    List<Commune> findWithMesuresById(@Param("ids") List<Long> ids);

    @Query("""
            SELECT c.id FROM Commune c
            WHERE c.codeInsee = :codeInsee
              AND EXISTS (
                  SELECT 1 FROM MesurePopulation mp
                  JOIN mp.mesure m
                  WHERE m.coordonnee = c.coordonnee
                    AND m.typeMesure = 'RELEVE_POPULATION'
                    AND mp.mesure.dateReleve = (
                        SELECT MAX(mp2.mesure.dateReleve)
                        FROM MesurePopulation mp2
                        WHERE mp2.mesure.coordonnee = c.coordonnee
                    )
              )
            """)
    Long findCommuneIdByCodeInsee(@Param("codeInsee") String codeInsee);

    @Query("""
            SELECT DISTINCT c FROM Commune c
            JOIN FETCH c.coordonnee cd
            JOIN FETCH cd.mesures m
            LEFT JOIN FETCH m.mesuresAir ma
            LEFT JOIN FETCH m.mesuresPrev mp
            LEFT JOIN FETCH m.mesuresPop mpop
            LEFT JOIN FETCH c.departement d
            LEFT JOIN FETCH d.region r
            WHERE c.id = :id
            AND (
                (m.typeMesure = 'RELEVE_AIR' AND ma.mesure.dateReleve = (
                    SELECT MAX(ma2.mesure.dateReleve)
                    FROM MesureAir ma2
                    WHERE ma2.mesure.coordonnee = cd
                ))
                OR
                (m.typeMesure = 'RELEVE_METEO' AND mp.mesure.dateReleve = (
                    SELECT MAX(mp2.mesure.dateReleve)
                    FROM MesurePrevision mp2
                    WHERE mp2.mesure.coordonnee = cd
                ))
                OR
                (m.typeMesure = 'RELEVE_POPULATION' AND mpop.mesure.dateReleve = (
                    SELECT MAX(mpop2.mesure.dateReleve)
                    FROM MesurePopulation mpop2
                    WHERE mpop2.mesure.coordonnee = cd
                ))
            )
            """)
    Commune findWithMesuresById(@Param("id") Long id);

    List<Commune> findTop10ByNomSimpleContainingIgnoreCase(String attr0);

    @Query("""
            SELECT DISTINCT c FROM Commune c
            JOIN FETCH c.coordonnee cd
            JOIN FETCH cd.mesures m
            JOIN FETCH m.mesuresPrev mp
            LEFT JOIN FETCH c.departement d
            LEFT JOIN FETCH d.region r
            WHERE c.id = :id
            AND m.typeMesure = 'RELEVE_METEO'
            AND mp.typeReleve = 'PREVISION_5J'
            AND mp.datePrevision <= :endDate
            AND mp.datePrevision >= :startDate
            ORDER BY mp.datePrevision
            """)
    Commune findWithForecastMesuresById(@Param("id") Long id,
                                        @Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);
}
