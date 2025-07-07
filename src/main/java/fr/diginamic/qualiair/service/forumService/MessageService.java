package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.entity.forum.ReactionType;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageService {
    /**
     * Récupère tous les messages présents en base.
     *
     * @return liste de MessageDto représentant tous les messages.
     */
    Page<MessageDto> getAllMessages(Pageable pageable);

    /**
     * Récupère la liste de tous les messages associés à un topic
     *
     * @param idTopic désigne l'id du topic parent
     * @return la liste de tous les messages associés à ce topic
     */
    List<MessageDto> getMessagesByTopic(Long idTopic);

    /**
     * Crée un nouveau message dans un topic existant.
     *
     * @param dto      les données du message à créer.
     * @param createur l'utilisateur connecté, auteur du message.
     * @return le message créé sous forme de DTO.
     * @throws BusinessRuleException    si le message ne respecte pas les règles métier.
     * @throws IllegalArgumentException si le topic lié est introuvable.
     * @throws FileNotFoundException    si le topic associé n'est pas trouvé
     */
    MessageDto createMessage(MessageDto dto, Utilisateur createur)
            throws BusinessRuleException, FileNotFoundException, TokenExpiredException;

    /**
     * Met à jour un message existant si l'utilisateur est l'auteur ou un administrateur.
     *
     * @param idMessage    identifiant du message à modifier.
     * @param dto          les nouvelles données du message.
     * @param modificateur l'utilisateur connecté tentant la modification.
     * @return le message modifié sous forme de DTO.
     * @throws AccessDeniedException    si l'utilisateur n'est ni admin ni auteur du message.
     * @throws IllegalArgumentException si le message est introuvable ou invalide.
     * @throws BusinessRuleException    si le message modifié ne respecte pas les règles métier.
     * @throws FileNotFoundException    si le message n'est pas trouvé
     */
    MessageDto updateMessage(Long idMessage, MessageDto dto, Utilisateur modificateur)
            throws BusinessRuleException, FileNotFoundException, TokenExpiredException;

    /**
     * Suppression d'un message existant si l'utilisateur est l'auteur ou un administrateur.
     *
     * @param idMessage identifiant du message à supprimer.
     * @param user      l'utilisateur connecté tentant la suppression.
     * @throws FileNotFoundException si le message n'est pas trouvé
     */
    void deleteMessage(Long idMessage, Utilisateur user) throws FileNotFoundException;

    /**
     * Incrémente l'attribut de Message concerné par la réaction associée au message
     *
     * @param messageId identifiant du message à l'origine de la réaction
     * @param user      utilisateur qui réagit au message
     * @param type      type de réaction (like, dislike ou report)
     * @return la confirmation contenant le message à l'origine de la réaction
     * @throws BusinessRuleException si ce type de réaction à ce message a déjà été fait par cet utilisateur
     * @throws FileNotFoundException si le message n'est pas trouvé
     */
    @Transactional
    MessageDto reactToMessage(Long messageId, Utilisateur user, ReactionType type)
            throws FileNotFoundException, BusinessRuleException;

    /**
     * Décrémente l'attribut de Message concerné par la réaction associée au message
     *
     * @param messageId identifiant du message à l'origine de la réaction
     * @param user      utilisateur qui annule une précédente réaction au message
     * @param type      type de réaction à annuler (like, dislike ou report)
     * @return la confirmation contenant le message duquel la réaction a été annulée
     * @throws BusinessRuleException si ce type de réaction à ce message n'a pas été fait par cet utilisateur
     * @throws FileNotFoundException si le message n'est pas trouvé
     */
    @Transactional
    MessageDto removeReaction(Long messageId, Utilisateur user, ReactionType type)
            throws FileNotFoundException, BusinessRuleException;
}
