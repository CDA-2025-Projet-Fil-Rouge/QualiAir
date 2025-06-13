package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.MesurePopulation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesurePopulationRepository extends JpaRepository<MesurePopulation, Long> {
}
