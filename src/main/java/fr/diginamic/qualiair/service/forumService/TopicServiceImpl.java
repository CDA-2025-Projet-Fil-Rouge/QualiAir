package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.entity.forum.Rubrique;
import fr.diginamic.qualiair.entity.forum.Topic;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import fr.diginamic.qualiair.mapper.forumMapper.TopicMapper;
import fr.diginamic.qualiair.repository.MessageRepository;
import fr.diginamic.qualiair.repository.RubriqueRepository;
import fr.diginamic.qualiair.repository.TopicRepository;
import fr.diginamic.qualiair.utils.CheckUtils;
import fr.diginamic.qualiair.utils.ForumUtils;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import fr.diginamic.qualiair.validator.forumValidator.TopicValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service de gestion des topics (sujets) du forum.
 * Permet la récupération et la création de nouveaux topics associés à une rubrique.
 */
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private RubriqueRepository rubriqueRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private TopicValidator topicValidator;

    @Override
    public Page<TopicDto> getAllTopics(Pageable pageable) {
        return topicRepository.findAll(pageable).map(topicMapper::toDto);
    }

    @Override
    public List<TopicDto> getTopicsByRubrique(Long idRubrique) {
        return topicRepository.findByRubriqueId(idRubrique).stream()
                .map(topicMapper::toDto)
                .toList();
    }

    @Override
    public TopicDto createTopic(TopicDto dto, Utilisateur createur)
            throws BusinessRuleException, FileNotFoundException, TokenExpiredException {
        Topic topic = topicMapper.toEntity(dto);
        topic.setCreateur(createur);
        topic.setDateCreation(LocalDateTime.now());

        Rubrique rubrique = ForumUtils.findRubriqueOrThrow(rubriqueRepository, dto.getIdRubrique());
        topic.setRubrique(rubrique);
        topicValidator.validate(topic);
        topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    @Override
    public TopicDto updateTopic(Long idTopic, TopicDto dto, Utilisateur modificateur)
            throws BusinessRuleException, FileNotFoundException, TokenExpiredException {
        CheckUtils.ensureMatchingIds(idTopic, dto.getId());
        Topic topic = ForumUtils.findTopicOrThrow(topicRepository, idTopic);

        UtilisateurUtils.checkAuthorOrAdmin(modificateur, topic.getCreateur().getId());
        Rubrique rubrique = ForumUtils.findRubriqueOrThrow(rubriqueRepository, dto.getIdRubrique());

        topic.setNom(dto.getNom());
        topic.setRubrique(rubrique);
        topic.setDateModification(LocalDateTime.now());
        topic.setModificateur(modificateur);
        topicValidator.validate(topic);
        topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    @Override
    public void deleteTopic(Long idTopic, Utilisateur user) throws FileNotFoundException, BusinessRuleException {
        Topic topicASupprimer = ForumUtils.findTopicOrThrow(topicRepository, idTopic);
        UtilisateurUtils.isAdmin(user);
        ForumUtils.assertTopicIsEmpty(messageRepository, idTopic);
        topicRepository.delete(topicASupprimer);
    }
}
