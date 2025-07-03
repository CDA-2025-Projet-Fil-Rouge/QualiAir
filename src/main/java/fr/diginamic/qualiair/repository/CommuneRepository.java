package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
            LEFT JOIN FETCH c.departement d
            LEFT JOIN FETCH d.region r
            WHERE EXISTS (
                SELECT 1 FROM MesurePopulation mp
                WHERE mp.coordonnee = cd
                  AND mp.valeur >= :nbHab
                  AND mp.dateReleve = (
                      SELECT MAX(mp2.dateReleve)
                      FROM MesurePopulation mp2
                      WHERE mp2.coordonnee = cd
                  )
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

    @Query(value = """
            SELECT c.id FROM Commune c
            WHERE c.codeInsee NOT LIKE '97%'
              AND EXISTS (
                  SELECT 1 FROM MesurePopulation mp
                  WHERE mp.coordonnee = c.coordonnee
                    AND mp.valeur >= :nbHab
                    AND mp.dateReleve = (
                        SELECT MAX(mp2.dateReleve)
                        FROM MesurePopulation mp2
                        WHERE mp2.coordonnee = c.coordonnee
                    )
              )
            """)
    List<Long> findCommuneIdsByPopulation(@Param("nbHab") int nbHab);

    @Query("""
            SELECT DISTINCT c FROM Commune c
            JOIN FETCH c.coordonnee cd
            JOIN FETCH cd.mesures m
            LEFT JOIN FETCH c.departement d
            LEFT JOIN FETCH d.region r
            WHERE c.id IN :ids
              AND c.codeInsee NOT LIKE '97%'
              AND (
                  (m.typeMesure = 'RELEVE_AIR' AND m.dateReleve = (
                      SELECT MAX(m2.dateReleve)
                      FROM Mesure m2
                      WHERE m2.typeMesure = 'RELEVE_AIR' AND m2.coordonnee = cd
                  ))
                  OR
                  (m.typeMesure = 'RELEVE_METEO' AND m.dateReleve = (
                      SELECT MAX(m3.dateReleve)
                      FROM Mesure m3
                      WHERE m3.typeMesure = 'RELEVE_METEO' AND m3.coordonnee = cd
                  ))
                  OR
                  (m.typeMesure = 'RELEVE_POPULATION' AND m.dateReleve = (
                      SELECT MAX(m4.dateReleve)
                      FROM Mesure m4
                      WHERE m4.typeMesure = 'RELEVE_POPULATION' AND m4.coordonnee = cd
                  ))
              )
            """)
    List<Commune> findWithMesuresById(@Param("ids") List<Long> ids);

    @Query(value = """
            SELECT c.id FROM Commune c
            WHERE c.codeInsee = :codeInsee
              AND EXISTS (
                  SELECT 1 FROM MesurePopulation mp
                  WHERE mp.coordonnee = c.coordonnee
                    AND mp.dateReleve = (
                        SELECT MAX(mp2.dateReleve)
                        FROM MesurePopulation mp2
                        WHERE mp2.coordonnee = c.coordonnee
                    )
              )
            """)
    Long findCommuneIdByCodeInsee(@Param("codeInsee") String codeInsee);

    @Query("""
            SELECT c FROM Commune c
            JOIN FETCH c.coordonnee cd
            JOIN FETCH cd.mesures m
            LEFT JOIN FETCH c.departement d
            LEFT JOIN FETCH d.region r
            WHERE c.id = :id
            AND (
                (m.typeMesure = 'RELEVE_AIR' AND m.dateReleve = (
                    SELECT MAX(m2.dateReleve)
                    FROM Mesure m2
                    WHERE m2.typeMesure = 'RELEVE_AIR' AND m2.coordonnee = cd
                ))
                OR
                (m.typeMesure = 'RELEVE_METEO' AND m.dateReleve = (
                    SELECT MAX(m3.dateReleve)
                    FROM Mesure m3
                    WHERE m3.typeMesure = 'RELEVE_METEO' AND m3.coordonnee = cd
                ))
                OR
                (m.typeMesure = 'RELEVE_POPULATION' AND m.dateReleve = (
                    SELECT MAX(m4.dateReleve)
                    FROM Mesure m4
                    WHERE m4.typeMesure = 'RELEVE_POPULATION' AND m4.coordonnee = cd
                ))
            )
            """)
    Commune findWithMesuresById(@Param("id") Long id);

    List<Commune> findTop10ByNomSimpleContainingIgnoreCase(String attr0);
}
