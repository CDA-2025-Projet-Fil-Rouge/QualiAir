package fr.diginamic.qualiair.validator.forumValidator;

import fr.diginamic.qualiair.entity.Message;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.validator.IValidator;

public interface IMessageValidator extends IValidator<Message> {
    /**
     * Valide une entité de type Message.
     * @param message le message à valider
     * @throws BusinessRuleException si une règle métier n'est pas respectée
     */
    @Override
    void validate(Message message) throws BusinessRuleException;

}
