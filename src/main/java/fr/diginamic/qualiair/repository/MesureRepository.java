package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Mesure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesureRepository extends JpaRepository<Mesure, Long>
{
}
