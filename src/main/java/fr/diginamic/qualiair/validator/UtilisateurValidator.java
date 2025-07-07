package fr.diginamic.qualiair.validator;

import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Validator métier pour l'entité Utilisateur.
 * Vérifie les contraintes de base lors de la création d'un nouvel utilisateur,
 * comme la présence des champs obligatoires et l'unicité de l'email.
 */
@Component
public class UtilisateurValidator implements IUtilisateurValidator {

    @Autowired
    UtilisateurRepository utilisateurRepository;

    /**
     * Valide les règles métier liées à la création d'un utilisateur.
     * Vérifie que tous les champs obligatoires sont présents et que l'email est unique.
     *
     * @param user L'utilisateur à valider
     * @return true si toutes les règles sont respectées
     * @throws BusinessRuleException si une règle métier est violée
     */
    @Override
    public boolean validate(Utilisateur user) throws BusinessRuleException {
        isTrue(user.getEmail() != null && !user.getEmail().isBlank(), "L'email est obligatoire.");
        isTrue(user.getMotDePasse() != null && !user.getMotDePasse().isBlank(), "Le mot de passe est obligatoire.");
        isTrue(user.getPrenom() != null && !user.getPrenom().isBlank(), "Le prénom est obligatoire.");
        isTrue(user.getNom() != null && !user.getNom().isBlank(), "Le nom est obligatoire.");
        isTrue(user.getAdresse() != null, "L'adresse est obligatoire.");
        isTrue(utilisateurRepository.findByEmail(user.getEmail()).isEmpty(),
                "Un utilisateur avec cet email existe déjà.");
        return true;
    }

}
