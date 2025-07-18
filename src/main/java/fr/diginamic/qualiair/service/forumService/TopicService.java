package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.DataNotFoundException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.exception.TokenExpiredException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

public interface TopicService {
    /**
     * Récupère tous les topics existants.
     *
     * @return une liste de TopicDto représentant les topics du forum.
     */
    Page<TopicDto> getAllTopics(Pageable pageable);

    /**
     * Récupère tous les topics existants d'une rubrique
     *
     * @param idRubrique désigne l'id de la rubrique parente
     * @return la liste des topics associés à la rubrique indiquée
     */
    List<TopicDto> getTopicsByRubrique(Long idRubrique);

    /**
     * Récupère un topic avec son id
     * @param idTopic désigne l'identifiant du topic à retrouver
     * @return le topicDto du topic à retrouver
     * @throws DataNotFoundException si le topic n'est pas retrouvé
     */
    TopicDto getTopicById(Long idTopic) throws DataNotFoundException;

    /**
     * Crée un nouveau topic dans une rubrique donnée.
     *
     * @param dto      les informations du topic à créer.
     * @param createur l'utilisateur connecté qui crée le topic.
     * @return le topic créé sous forme de DTO.
     * @throws IllegalArgumentException si la rubrique associée n'existe pas.
     * @throws BusinessRuleException    si le topic ne respecte pas les règles métier.
     * @throws FileNotFoundException    si la rubrique associée n'est pas trouvée.
     */
    TopicDto createTopic(TopicDto dto, Utilisateur createur)
            throws BusinessRuleException, FileNotFoundException, TokenExpiredException;

    /**
     * Met à jour un topic existant si l'utilisateur est l'auteur ou un administrateur.
     *
     * @param idTopic      identifiant du topic à modifier.
     * @param dto          les nouvelles données du topic.
     * @param modificateur l'utilisateur connecté tentant la modification.
     * @return le topic modifié sous forme de DTO.
     * @throws AccessDeniedException    si l'utilisateur n'est ni admin ni auteur du topic.
     * @throws IllegalArgumentException si le topic est introuvable ou invalide.
     * @throws BusinessRuleException    si le topic modifié ne respecte pas les règles métier.
     */
    TopicDto updateTopic(Long idTopic, TopicDto dto, Utilisateur modificateur)
            throws BusinessRuleException, FileNotFoundException, TokenExpiredException;

    /**
     * Supprime un topic existant, à la condition que l'utilisateur soit admin et que le topic ne contienne pas de message
     *
     * @param idTopic identifiant du topic à supprimer
     * @param user    l'utilisateur connecté tentant la suppression.
     * @throws AccessDeniedException si l'utilisateur n'est pas admin.
     * @throws FileNotFoundException si le topic est introuvable ou invalide
     * @throws BusinessRuleException si la tentative de suppression ne respecte pas les règles métier.
     */
    void deleteTopic(Long idTopic, Utilisateur user) throws FileNotFoundException, BusinessRuleException;
}
