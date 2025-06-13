package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.entity.Message;
import fr.diginamic.qualiair.entity.Rubrique;
import fr.diginamic.qualiair.entity.Topic;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.mapper.forumMapper.TopicMapper;
import fr.diginamic.qualiair.repository.RubriqueRepository;
import fr.diginamic.qualiair.repository.TopicRepository;
import fr.diginamic.qualiair.utils.ForumUtils;
import fr.diginamic.qualiair.utils.UtilisateurUtils;
import fr.diginamic.qualiair.validator.forumValidator.TopicValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service de gestion des topics (sujets) du forum.
 * Permet la récupération et la création de nouveaux topics associés à une rubrique.
 */
@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private RubriqueRepository rubriqueRepository;
    @Autowired
    private TopicValidator topicValidator;

    /**
     * Récupère tous les topics existants.
     *
     * @return une liste de TopicDto représentant les topics du forum.
     */
    public List<TopicDto> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(topic -> topicMapper.toDto(topic))
                .toList();
    }

    /**
     * Crée un nouveau topic dans une rubrique donnée.
     *
     * @param dto les informations du topic à créer.
     * @param createur l'utilisateur connecté qui crée le topic.
     * @return le topic créé sous forme de DTO.
     * @throws IllegalArgumentException si la rubrique associée n'existe pas.
     * @throws BusinessRuleException si le topic ne respecte pas les règles métier.
     * @throws FileNotFoundException si la rubrique associée n'est pas trouvée.
     */
    public TopicDto createTopic(TopicDto dto, Utilisateur createur)
            throws BusinessRuleException, FileNotFoundException {
        Topic topic = topicMapper.toEntity(dto);
        topic.setCreateur(createur);
        topic.setDateCreation(LocalDateTime.now());

        Rubrique rubrique = ForumUtils.findRubriqueOrThrow(rubriqueRepository, dto.getId());
        topic.setRubrique(rubrique);
        topicValidator.validate(topic);
        topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }

    /**
     * Met à jour un topic existant si l'utilisateur est l'auteur ou un administrateur.
     *
     * @param idTopic identifiant du topic à modifier.
     * @param dto les nouvelles données du topic.
     * @param modificateur l'utilisateur connecté tentant la modification.
     * @return le topic modifié sous forme de DTO.
     * @throws AccessDeniedException si l'utilisateur n'est ni admin ni auteur du topic.
     * @throws IllegalArgumentException si le topic est introuvable ou invalide.
     * @throws BusinessRuleException si le topic modifié ne respecte pas les règles métier.
     */
    public TopicDto updateTopic(Long idTopic, TopicDto dto, Utilisateur modificateur)
            throws BusinessRuleException, FileNotFoundException {
        ForumUtils.ensureMatchingIds(idTopic, dto.getId());
        Topic topic = ForumUtils.findTopicOrThrow(topicRepository, idTopic);

        UtilisateurUtils.checkAuthorOrAdmin(modificateur, dto.getIdCreateur());
        Rubrique rubrique = ForumUtils.findRubriqueOrThrow(rubriqueRepository, dto.getIdRubrique());

        topic.setNom(dto.getNom());
        topic.setRubrique(rubrique);
        topic.setDateModification(LocalDateTime.now());
        topic.setModificateur(modificateur);
        topicValidator.validate(topic);
        topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }
}
