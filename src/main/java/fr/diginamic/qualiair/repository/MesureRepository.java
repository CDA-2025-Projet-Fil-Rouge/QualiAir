package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Mesure;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Mesure repository
 */
public interface MesureRepository extends JpaRepository<Mesure, Long> {
}
