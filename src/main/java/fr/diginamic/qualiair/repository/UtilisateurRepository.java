package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Utilisateur repository
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);

    @Query("""
                 SELECT u.email FROM Utilisateur u
                 WHERE u.adresse.commune.codeInsee = :code
            """)
    List<String> findByAdresse_Commune_CodeInsee(String code);

    @Query("""
                 SELECT u.email FROM Utilisateur u
                 WHERE u.adresse.commune.departement.code = :code
            """)
    List<String> findByAdresse_Commune_Departement_Code(String code);

    @Query("""
                 SELECT u.email FROM Utilisateur u
                 WHERE u.adresse.commune.departement.region.code = :code
            """)
    List<String> findByAdresse_Commune_Departement_Region_Code(int code);

    List<String> findAllEmails();
}
