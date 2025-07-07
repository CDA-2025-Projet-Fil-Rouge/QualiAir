package fr.diginamic.qualiair.dao;

import fr.diginamic.qualiair.entity.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DepartementDao {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public Departement save(Departement departement) {
        em.persist(departement);
        return departement;
    }
}
