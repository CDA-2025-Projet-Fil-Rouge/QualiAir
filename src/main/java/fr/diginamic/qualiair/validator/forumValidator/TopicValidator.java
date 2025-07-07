package fr.diginamic.qualiair.validator.forumValidator;

import fr.diginamic.qualiair.entity.forum.Topic;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import org.springframework.stereotype.Component;

/**
 * Validator métier pour la vérification des règles de gestion d'un Topic.
 * S'assure que le topic possède les informations minimales nécessaires.
 */
@Component
public class TopicValidator implements ITopicValidator{

    /**
     * Valide les contraintes métier sur un topic.
     *
     * @param topic le topic à valider
     * @throws BusinessRuleException si une des règles métier n'est pas respectée
     */
    @Override
    public boolean validate(Topic topic) throws BusinessRuleException, TokenExpiredException {
        isTrue(topic.getNom() != null && !topic.getNom().isBlank(), "Le nom du topic est obligatoire.");
        isTrue(topic.getRubrique() != null, "Le topic doit appartenir à une rubrique.");

        return true;
    }
}
