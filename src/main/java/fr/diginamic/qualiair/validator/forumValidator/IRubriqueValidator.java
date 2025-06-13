package fr.diginamic.qualiair.validator.forumValidator;

import fr.diginamic.qualiair.entity.Rubrique;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.validator.IValidator;


public interface IRubriqueValidator extends IValidator<Rubrique> {
    /**
     * Valide une entité de type Rubrique.
     * @param rubrique la rubrique à valider
     * @throws BusinessRuleException si une règle métier n'est pas respectée
     */
    @Override
    void validate(Rubrique rubrique) throws BusinessRuleException;
}
