package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.entity.forum.Message;
import fr.diginamic.qualiair.entity.forum.Rubrique;
import fr.diginamic.qualiair.entity.forum.Topic;
import fr.diginamic.qualiair.exception.BusinessRuleException;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.repository.MessageRepository;
import fr.diginamic.qualiair.repository.RubriqueRepository;
import fr.diginamic.qualiair.repository.TopicRepository;

/**
 * Classe utilitaire pour les classes de services dédiées au Forum :
 * Concerne les entités Rubrique, Topic et Message
 * Recherche d'entité par Id grâce à leur repository, sinon FileNotFoundException
 */
public final class ForumUtils {

    private ForumUtils() {}

    /**
     * Récupère une rubrique par son identifiant ou déclenche une exception si elle n'existe pas.
     *
     * @param id identifiant de la rubrique
     * @return la rubrique trouvée
     * @throws FileNotFoundException si aucune rubrique n'est trouvée
     */
    public static Rubrique findRubriqueOrThrow(RubriqueRepository repo, Long id) throws FileNotFoundException {
        return repo.findById(id)
                .orElseThrow(() -> new FileNotFoundException("Rubrique introuvable"));
    }
    /**
     * Récupère un topic par son identifiant ou déclenche une exception s'il n'existe pas.
     *
     * @param id identifiant du topic
     * @return le topic trouvé
     * @throws FileNotFoundException si aucun topic n'est trouvé
     */
    public static Topic findTopicOrThrow(TopicRepository repo, Long id) throws FileNotFoundException {
        return repo.findById(id)
                .orElseThrow(() -> new FileNotFoundException("Topic introuvable"));
    }
    /**
     * Récupère un message par son identifiant ou déclenche une exception s'il n'existe pas.
     *
     * @param id identifiant du message
     * @return le message trouvé
     * @throws FileNotFoundException si aucun message n'est trouvé
     */
    public static Message findMessageOrThrow(MessageRepository repo, Long id) throws FileNotFoundException {
        return repo.findById(id)
                .orElseThrow(() -> new FileNotFoundException("Message introuvable"));
    }

    /**
     * Vérifie que deux identifiants correspondent.
     *
     * @param entityId l'identifiant de l'entité (souvent extrait de l'URL).
     * @param dtoId    l'identifiant provenant du corps de la requête (DTO).
     * @throws IllegalArgumentException si les deux identifiants ne sont pas égaux.
     */
    public static void ensureMatchingIds(Long entityId, Long dtoId) {
        if (!entityId.equals(dtoId)) {
            throw new IllegalArgumentException("L'identifiant de l'URL et celui du corps ne correspondent pas.");
        }
    }

    /**
     * Vérifie si une rubrique est vide (ne contient aucun topic).
     * @param topicRepository repository des topics
     * @param idRubrique identifiant de la rubrique
     * @throws IllegalStateException si des topics sont liés à cette rubrique
     */
    public static void assertRubriqueIsEmpty(TopicRepository topicRepository, Long idRubrique)
    throws  BusinessRuleException {
        if (topicRepository.countByRubriqueId(idRubrique) > 0) {
            throw new BusinessRuleException("La rubrique contient des topics et ne peut pas être supprimée.");
        }
    }

    /**
     * Vérifie si un topic est vide (ne contient aucun message).
     * @param messageRepository repository des messages
     * @param idTopic identifiant du topic
     * @throws IllegalStateException si des messages sont liés à ce topic
     */
    public static void assertTopicIsEmpty(MessageRepository messageRepository, Long idTopic)
    throws BusinessRuleException {
        if (messageRepository.countByTopicId(idTopic) > 0) {
            throw new BusinessRuleException("Le topic contient des messages et ne peut pas être supprimé.");
        }
    }
}
