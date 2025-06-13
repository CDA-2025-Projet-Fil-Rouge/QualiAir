package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Departement repository
 */
@Repository
public interface DepartementRepository extends JpaRepository<Departement, Long> {
}
