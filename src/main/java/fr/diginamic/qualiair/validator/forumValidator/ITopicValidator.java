package fr.diginamic.qualiair.validator.forumValidator;

import fr.diginamic.qualiair.entity.Topic;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.validator.IValidator;

public interface ITopicValidator extends IValidator<Topic> {
    /**
     * Valide une entité de type Topic.
     * @param topic le topic à valider
     * @throws BusinessRuleException si une règle métier n'est pas respectée
     */
    @Override
    void validate(Topic topic) throws BusinessRuleException;
}
