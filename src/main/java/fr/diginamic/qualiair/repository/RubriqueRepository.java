package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.forum.Rubrique;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RubriqueRepository extends JpaRepository<Rubrique, Long> {
}
