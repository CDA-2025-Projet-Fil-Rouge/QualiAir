package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.forum.Rubrique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RubriqueRepository extends JpaRepository<Rubrique, Long> {

    @Query("SELECT MAX(r.prioriteAffichageIndice) FROM Rubrique r")
    Optional<Integer> findMaxPrioriteIndice();
}
