package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Region repository
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}
