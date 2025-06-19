package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.entity.forum.Message;
import fr.diginamic.qualiair.entity.forum.ReactionType;
import fr.diginamic.qualiair.exception.BusinessRuleException;

public interface ReactionMessageService {
    /**
     * Créé une entité réaction lorsqu'un utilisateur réagit à un message
     * avec une condition d'unicité (utilisateur, message et type de réaction)
     *
     * @param user         utilisateur à l'origine de la réaction
     * @param message      message ciblé par la réaction
     * @param reactionType type de réaction (like, dislike ou report)
     * @throws BusinessRuleException si l'unicité n'est pas respectée
     */
    void createReaction(
            Utilisateur user,
            Message message,
            ReactionType reactionType) throws BusinessRuleException;

    /**
     * Retirer une réaction préalablement enregistrée par un utilisateur concernant un message
     *
     * @param user    utilisateur à l'origine de la réaction
     * @param message message concerné par la réaction à annuler
     * @param type    type de réaction (like, dislike, report)
     * @throws BusinessRuleException si la réaction n'a jamais été faite
     */
    void removeReaction(Utilisateur user, Message message, ReactionType type)
            throws BusinessRuleException;
}
