package fr.diginamic.qualiair.dao;

import fr.diginamic.qualiair.entity.Commune;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Dao
 */
@Repository
public class CommuneDao {
    @PersistenceContext
    EntityManager em;

    /**
     * Save sans merge
     *
     * @param commune commune
     * @return commune sauvegardée
     */
    @Transactional
    public Commune save(Commune commune) {
        em.persist(commune);
        return commune;
    }
}
