package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
