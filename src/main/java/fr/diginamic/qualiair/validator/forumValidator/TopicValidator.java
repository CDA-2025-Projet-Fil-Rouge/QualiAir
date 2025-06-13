package fr.diginamic.qualiair.validator.forumValidator;

import fr.diginamic.qualiair.entity.Topic;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import org.springframework.stereotype.Component;

@Component
public class TopicValidator implements ITopicValidator{

    @Override
    public void validate(Topic topic) throws BusinessRuleException {
        isTrue(topic.getNom() != null && !topic.getNom().isBlank(), "Le nom du topic est obligatoire.");
        isTrue(topic.getRubrique() != null, "Le topic doit appartenir à une rubrique.");
    }
}
