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


    @Query("""
                SELECT c FROM Commune c
                JOIN FETCH c.coordonnee
                JOIN FETCH c.departement d
                JOIN FETCH d.region
            """)
    List<Commune> findAll();

    Commune getCommuneByCoordonnee_LatitudeAndCoordonneeLongitude(double coordonneeLatitude, double coordonneeLongitude);

    Commune findCommuneByNomPostal(String nomPostal);

    @Query("SELECT DISTINCT c FROM Commune c JOIN FETCH Coordonnee cd JOIN FETCH Mesure m JOIN FETCH MesurePopulation mp WHERE mp.valeur >= :nbHab")
    List<Commune> findTopByMesurePopulation(@Param("nbhab") int nbhab);
}
