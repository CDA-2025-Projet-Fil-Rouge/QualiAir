package fr.diginamic.qualiair.validator.forumValidator;

import fr.diginamic.qualiair.entity.forum.Message;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import org.springframework.stereotype.Component;

/**
 * Validator métier pour la vérification des règles de gestion d'un Message.
 * S'assure que les champs obligatoires sont bien renseignés.
 */
@Component
public class MessageValidator implements IMessageValidator {

    /**
     * Valide les contraintes métier sur un message.
     *
     * @param message le message à valider
     * @throws BusinessRuleException si une des règles métier n'est pas respectée
     */
    @Override
    public boolean validate(Message message) throws BusinessRuleException, TokenExpiredException {
        isTrue(message.getContenu() != null && !message.getContenu().isBlank(), "Le contenu du message est obligatoire.");
        isTrue(message.getTopic() != null, "Le message doit être lié à un topic.");

        return true;
    }
}
