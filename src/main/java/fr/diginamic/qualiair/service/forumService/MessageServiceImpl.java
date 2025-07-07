package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.entity.forum.Message;
import fr.diginamic.qualiair.entity.forum.ReactionType;
import fr.diginamic.qualiair.entity.forum.Topic;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import fr.diginamic.qualiair.mapper.forumMapper.MessageMapper;
import fr.diginamic.qualiair.repository.MessageRepository;
import fr.diginamic.qualiair.repository.TopicRepository;
import fr.diginamic.qualiair.utils.CheckUtils;
import fr.diginamic.qualiair.utils.ForumUtils;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import fr.diginamic.qualiair.validator.forumValidator.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service métier pour la gestion des messages du forum.
 * Gère la création, la mise à jour, et la récupération des messages,
 * tout en assurant les règles métier et les contrôles d'accès.
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private MessageValidator messageValidator;
    @Autowired
    private ReactionMessageService reactionService;

    @Override
    public Page<MessageDto> getAllMessages(Pageable pageable) {
        return messageRepository.findAll(pageable).map(messageMapper::toDto);
    }

    @Override
    public List<MessageDto> getMessagesByTopic(Long idTopic) {
        return messageRepository.findByTopicId(idTopic).stream()
                .map(messageMapper::toDto)
                .toList();
    }

    @Override
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

    @Override
    public MessageDto updateMessage(Long idMessage, MessageDto dto, Utilisateur modificateur)
            throws BusinessRuleException, FileNotFoundException, TokenExpiredException {

        CheckUtils.ensureMatchingIds(idMessage, dto.getId());
        Message message = ForumUtils.findMessageOrThrow(messageRepository, idMessage);
        UtilisateurUtils.checkAuthorOrAdmin(modificateur, message.getCreateur().getId());
        Topic topic = ForumUtils.findTopicOrThrow(topicRepository, dto.getIdTopic());

        message.setContenu(dto.getContenu());
        message.setTopic(topic);
        message.setDateModification(LocalDateTime.now());
        message.setModificateur(modificateur);
        messageValidator.validate(message);
        messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    @Override
    public void deleteMessage(Long idMessage, Utilisateur user) throws FileNotFoundException {
        Message messageASupprimer = ForumUtils.findMessageOrThrow(messageRepository, idMessage);
        UtilisateurUtils.checkAuthorOrAdmin(user, messageASupprimer.getCreateur().getId());

        messageRepository.delete(messageASupprimer);
    }

    @Transactional
    @Override
    public MessageDto reactToMessage(Long messageId, Utilisateur user, ReactionType type)
            throws FileNotFoundException, BusinessRuleException {

        Message message = ForumUtils.findMessageOrThrow(messageRepository, messageId);
        reactionService.createReaction(user, message, type);

        updateReactionCounter(message, type, +1);
        messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    @Transactional
    @Override
    public MessageDto removeReaction(Long messageId, Utilisateur user, ReactionType type)
            throws FileNotFoundException, BusinessRuleException {
        Message message = ForumUtils.findMessageOrThrow(messageRepository, messageId);
        reactionService.removeReaction(user, message, type);

        updateReactionCounter(message, type, -1);
        messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    /**
     * Met à jour le compteur correspondant à une réaction (like, dislike ou signalement) sur un message
     *
     * @param message message dont le compteur doit être mis à jour.
     * @param type    type de réaction à appliquer (LIKE, DISLIKE ou REPORT).
     * @param i       décalage à appliquer au compteur (+1 pour ajout, -1 pour suppression).
     */
    private void updateReactionCounter(Message message, ReactionType type, int i) {
        switch (type) {
            case LIKE -> message.setNbLike(Math.max(0, message.getNbLike() + i));
            case DISLIKE -> message.setNbDislike(Math.max(0, message.getNbDislike() + i));
            case REPORT -> message.setNbSignalement(Math.max(0, message.getNbSignalement() + i));
        }
    }
}
