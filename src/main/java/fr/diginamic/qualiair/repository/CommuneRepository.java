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
    List<Commune> findAll();


    @Query("SELECT DISTINCT c FROM Commune  c JOIN FETCH Coordonnee cd ON cd.commune = c WHERE c.nomPostal = :nomPostal")
    Commune findCommuneByNomPostal(@Param("nomPostal") String nomPostal);


    @Query(value = """
            SELECT DISTINCT c FROM Commune c
            JOIN FETCH c.coordonnee cd
            JOIN FETCH MesurePopulation mpop ON mpop.coordonnee = cd
            WHERE mpop.valeur >= :nbHab
              AND mpop.dateReleve = (
                  SELECT MAX(m2.dateReleve)
                  FROM MesurePopulation m2
                  WHERE m2.coordonnee = cd
              )
            """)
    List<Commune> findTopByLastestMesurePopulation(@Param("nbHab") int nbHab);

    @Query(value = """
            SELECT DISTINCT c FROM Commune c
            JOIN FETCH c.coordonnee cd
            
            JOIN FETCH MesurePopulation mpop ON mpop.coordonnee = cd
            JOIN FETCH MesurePrevision mprev ON mprev.coordonnee = cd
            JOIN FETCH MesureAir mair ON mair.coordonnee = cd
            
            WHERE mpop.valeur >= :nbHab
              AND mpop.dateReleve = (
                  SELECT MAX(m2.dateReleve)
                  FROM MesurePopulation m2
                  WHERE m2.coordonnee = cd
              )
              AND mprev.dateReleve = (
                  SELECT MAX(m2.dateReleve)
                  FROM MesurePrevision m2
                  WHERE m2.coordonnee = cd
              )
              AND mair.dateReleve = (
                  SELECT MAX(m2.dateReleve)
                  FROM MesureAir m2
                  WHERE m2.coordonnee = cd
              )
            """)
    List<Commune> findTopByMesurePopulationWithCurrentForecast(@Param("nbHab") int nbHab);

}
