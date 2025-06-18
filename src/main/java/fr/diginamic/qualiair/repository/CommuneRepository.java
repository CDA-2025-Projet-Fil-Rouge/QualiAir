package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Commune repository
 */
@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {


    @Query("SELECT c FROM Commune c JOIN FETCH c.coordonnee JOIN FETCH c.departement d JOIN FETCH d.region")
    List<Commune> findAllWithRelations();

    @Query(value = """
            SELECT DISTINCT c FROM Commune c
            JOIN FETCH c.coordonnee cd
            JOIN FETCH MesurePopulation mpop ON mpop.coordonnee = cd
            WHERE mpop.valeur >= :nbHab
              AND mpop.dateReleve = (SELECT m2.dateReleve FROM MesurePopulation m2 WHERE m2.coordonnee = cd ORDER BY m2.dateReleve DESC LIMIT 1)
            """)
    List<Commune> findTopByLastestMesurePopulation(@Param("nbHab") int nbHab);

    @Query(value = """
            SELECT DISTINCT c FROM Commune c
            JOIN FETCH c.coordonnee cd
            
            JOIN FETCH MesurePopulation mpop ON mpop.coordonnee = cd
            JOIN FETCH MesurePrevision mprev ON mprev.coordonnee = cd
            JOIN FETCH MesureAir mair ON mair.coordonnee = cd
            
            WHERE mpop.valeur >= :nbHab
              AND mpop.dateReleve = (SELECT m2.dateReleve FROM MesurePopulation m2 WHERE m2.coordonnee = cd ORDER BY m2.dateReleve DESC LIMIT 1)
              AND mprev.dateReleve = (SELECT m2.dateReleve FROM MesurePrevision m2 WHERE m2.coordonnee = cd ORDER BY m2.dateReleve DESC LIMIT 1)
              AND mair.dateReleve = (SELECT m2.dateReleve FROM MesureAir m2 WHERE m2.coordonnee = cd ORDER BY m2.dateReleve DESC LIMIT 1)
            """)
    List<Commune> findTopByMesurePopulationWithCurrentForecastWithAllReleveRelations(@Param("nbHab") int nbHab);


    @Query(value = """
            SELECT DISTINCT c FROM Commune c
            JOIN FETCH c.coordonnee cd
            
            JOIN FETCH MesurePopulation mpop ON mpop.coordonnee = cd
            LEFT JOIN FETCH MesurePrevision mprev ON mprev.coordonnee = cd AND mprev.dateReleve = (SELECT m2.dateReleve FROM MesurePrevision m2 WHERE m2.coordonnee = cd ORDER BY m2.dateReleve DESC LIMIT 1)
            LEFT JOIN FETCH MesureAir mair ON mair.coordonnee = cd AND mair.dateReleve = (SELECT m2.dateReleve FROM MesureAir m2 WHERE m2.coordonnee = cd ORDER BY m2.dateReleve DESC LIMIT 1)
            
            WHERE mpop.valeur >= :nbHab
              AND mpop.dateReleve = (SELECT m2.dateReleve FROM MesurePopulation m2 WHERE m2.coordonnee = cd ORDER BY m2.dateReleve DESC LIMIT 1)
            """)
    List<Commune> findTopByMesurePopulationWithCurrentForecastWithOptionalReleveRelations(@Param("nbHab") int nbHab);

    @Query(value = """
            SELECT mp.coordonnee.id FROM MesurePopulation mp
            WHERE mp.valeur >= :nbHab
              AND mp.dateReleve = (
                  SELECT MAX(m2.dateReleve)
                  FROM MesurePopulation m2
                  WHERE m2.coordonnee = mp.coordonnee
              )
            """)
    List<Long> findCoordonneesWithLatestPopulation(@Param("nbHab") int nbHab);

    @Query(value = """
            SELECT DISTINCT c FROM Commune c
            JOIN FETCH c.coordonnee cd
            WHERE cd.id IN :coordonneeIds
            """)
    List<Commune> findCommunesByCoordonneeIds(@Param("coordonneeIds") List<Long> coordonneeIds);


    @Query("SELECT c FROM Commune c JOIN FETCH c.coordonnee")
    List<Commune> findAllWithCoordinates();
}
