package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Coordonnee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Coordinates repository
 */
@Repository
public interface CoordonneRepository extends JpaRepository<Coordonnee, Long> {
}
