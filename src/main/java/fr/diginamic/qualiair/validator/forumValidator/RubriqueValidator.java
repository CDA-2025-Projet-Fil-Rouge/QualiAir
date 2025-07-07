package fr.diginamic.qualiair.validator.forumValidator;

import fr.diginamic.qualiair.entity.forum.Rubrique;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import org.springframework.stereotype.Component;

/**
 * Validator métier pour la vérification des règles de gestion d'une {@link Rubrique}.
 * Vérifie que les champs requis sont bien fournis avant traitement.
 */
@Component
public class RubriqueValidator implements IRubriqueValidator {

    /**
     * Valide les contraintes métier sur une rubrique.
     *
     * @param rubrique la rubrique à valider
     * @throws BusinessRuleException si une des règles métier n'est pas respectée
     */
    @Override
    public boolean validate(Rubrique rubrique) throws BusinessRuleException, TokenExpiredException {
        isTrue(rubrique.getNom() != null && !rubrique.getNom().isBlank(), "Le nom est obligatoire.");
        isTrue(rubrique.getDescription() != null && !rubrique.getDescription().isBlank(), "La description est obligatoire.");

        return true;
    }
}
