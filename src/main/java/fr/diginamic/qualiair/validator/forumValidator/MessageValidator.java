package fr.diginamic.qualiair.validator.forumValidator;

import fr.diginamic.qualiair.entity.Message;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import org.springframework.stereotype.Component;

@Component
public class MessageValidator implements IMessageValidator {
    @Override
    public void validate(Message message) throws BusinessRuleException {
        isTrue(message.getContenu() != null && !message.getContenu().isBlank(), "Le contenu du message est obligatoire.");
        isTrue(message.getTopic() != null, "Le message doit être lié à un topic.");
    }
}
