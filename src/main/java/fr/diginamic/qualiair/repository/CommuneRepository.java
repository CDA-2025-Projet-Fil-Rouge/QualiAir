package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Commune repository
 */
@Repository
public interface CommuneRepository extends JpaRepository<Commune, Long> {
}
