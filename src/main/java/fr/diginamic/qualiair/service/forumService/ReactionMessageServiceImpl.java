package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.entity.forum.Message;
import fr.diginamic.qualiair.entity.forum.ReactionMessage;
import fr.diginamic.qualiair.entity.forum.ReactionType;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.repository.ReactionMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service métier pour la gestion des réactions aux messages du forum.
 */
@Service
public class ReactionMessageServiceImpl implements ReactionMessageService {

    @Autowired
    ReactionMessageRepository reactionRepository;

    @Override
    public void createReaction(
            Utilisateur user,
            Message message,
            ReactionType reactionType) throws BusinessRuleException {
        if (reactionRepository.existsByUtilisateurAndMessageAndType(user, message, reactionType)) {
            throw new BusinessRuleException("Vous avez déjà réagi à ce message");
        }

        ReactionMessage reaction = new ReactionMessage();
        reaction.setUtilisateur(user);
        reaction.setMessage(message);
        reaction.setType(reactionType);
        reaction.setDate(LocalDateTime.now());
        reactionRepository.save(reaction);
    }

    @Override
    public void removeReaction(Utilisateur user, Message message, ReactionType type)
            throws BusinessRuleException {
        ReactionMessage reaction = reactionRepository
                .findByUtilisateurAndMessageAndType(user, message, type)
                .orElseThrow(() -> new BusinessRuleException("Aucune réaction trouvée à retirer"));
        reactionRepository.delete(reaction);
    }
}
