package fr.diginamic.qualiair.dao;

import fr.diginamic.qualiair.entity.Coordonnee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CoordoneeDao {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public Coordonnee save(Coordonnee coordonee) {
        em.persist(coordonee);
        return coordonee;
    }
}
