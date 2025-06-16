package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.entity.Message;
import fr.diginamic.qualiair.entity.Topic;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import fr.diginamic.qualiair.mapper.forumMapper.MessageMapper;
import fr.diginamic.qualiair.repository.MessageRepository;
import fr.diginamic.qualiair.repository.TopicRepository;
import fr.diginamic.qualiair.utils.ForumUtils;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import fr.diginamic.qualiair.validator.forumValidator.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service métier pour la gestion des messages du forum.
 * Gère la création, la mise à jour, et la récupération des messages,
 * tout en assurant les règles métier et les contrôles d'accès.
 */
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private MessageValidator messageValidator;

    /**
     * Récupère tous les messages présents en base.
     * @return liste de MessageDto représentant tous les messages.
     */
    public Page<MessageDto> getAllMessages(Pageable pageable) {
        return messageRepository.findAll(pageable).map(messageMapper::toDto);
    }

    /**
     * Récupère la liste de tous les messages associés à un topic
     * @param idTopic désigne l'id du topic parent
     * @return la liste de tous les messages associés à ce topic
     */
    public List<MessageDto> getMessagesByTopic(Long idTopic) {
        return messageRepository.findByTopicId(idTopic).stream()
                .map(messageMapper::toDto)
                .toList();
    }

    /**
     * Crée un nouveau message dans un topic existant.
     *
     * @param dto les données du message à créer.
     * @param createur l'utilisateur connecté, auteur du message.
     * @return le message créé sous forme de DTO.
     * @throws BusinessRuleException si le message ne respecte pas les règles métier.
     * @throws IllegalArgumentException si le topic lié est introuvable.
     * @throws FileNotFoundException si le topic associé n'est pas trouvé
     */
    public MessageDto createMessage(MessageDto dto, Utilisateur createur)
            throws BusinessRuleException, FileNotFoundException, TokenExpiredException {
        Topic topic = ForumUtils.findTopicOrThrow(topicRepository, dto.getIdTopic());

        Message message = messageMapper.toEntity(dto);
        message.setCreateur(createur);
        message.setDateCreation(LocalDateTime.now());
        message.setTopic(topic);
        messageValidator.validate(message);
        messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    /**
     * Met à jour un message existant si l'utilisateur est l'auteur ou un administrateur.
     *
     * @param idMessage identifiant du message à modifier.
     * @param dto les nouvelles données du message.
     * @param modificateur l'utilisateur connecté tentant la modification.
     * @return le message modifié sous forme de DTO.
     * @throws AccessDeniedException si l'utilisateur n'est ni admin ni auteur du message.
     * @throws IllegalArgumentException si le message est introuvable ou invalide.
     * @throws BusinessRuleException si le message modifié ne respecte pas les règles métier.
     * @throws FileNotFoundException si le message n'est pas trouvé
     */
    public MessageDto updateMessage(Long idMessage, MessageDto dto, Utilisateur modificateur)
            throws BusinessRuleException, FileNotFoundException, TokenExpiredException {

        ForumUtils.ensureMatchingIds(idMessage, dto.getId());
        Message message = ForumUtils.findMessageOrThrow(messageRepository, idMessage);
        UtilisateurUtils.checkAuthorOrAdmin(modificateur,message.getCreateur().getId());
        Topic topic = ForumUtils.findTopicOrThrow(topicRepository, dto.getIdTopic());

        message.setContenu(dto.getContenu());
        message.setTopic(topic);
        message.setDateModification(LocalDateTime.now());
        message.setModificateur(modificateur);
        messageValidator.validate(message);
        messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    /**
     *  Suppression d'un message existant si l'utilisateur est l'auteur ou un administrateur.
     *
     * @param idMessage identifiant du message à supprimer.
     * @param user l'utilisateur connecté tentant la suppression.
     * @throws FileNotFoundException si le message n'est pas trouvé
     */
    public void deleteMessage(Long idMessage, Utilisateur user) throws FileNotFoundException {
        Message messageASupprimer = ForumUtils.findMessageOrThrow(messageRepository, idMessage);
        UtilisateurUtils.checkAuthorOrAdmin(user, messageASupprimer.getCreateur().getId());

        messageRepository.delete(messageASupprimer);
    }
}
