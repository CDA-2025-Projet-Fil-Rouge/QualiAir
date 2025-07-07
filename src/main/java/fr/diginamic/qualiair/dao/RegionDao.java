package fr.diginamic.qualiair.dao;

import fr.diginamic.qualiair.entity.Region;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RegionDao {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public Region save(Region region){
         em.persist(region);
         return region;
    }
}
