package fr.diginamic.qualiair.validator.forumValidator;

import fr.diginamic.qualiair.entity.Rubrique;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import org.springframework.stereotype.Component;

@Component
public class RubriqueValidator implements IRubriqueValidator {

    @Override
    public void validate(Rubrique rubrique) throws BusinessRuleException {
        isTrue(rubrique.getNom() != null && !rubrique.getNom().isBlank(), "Le nom est obligatoire.");
        isTrue(rubrique.getDescription() != null && !rubrique.getDescription().isBlank(), "La description est obligatoire.");
    }
}
