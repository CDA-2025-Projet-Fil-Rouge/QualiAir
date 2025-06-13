package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.entity.Message;
import fr.diginamic.qualiair.entity.Rubrique;
import fr.diginamic.qualiair.entity.Topic;
import fr.diginamic.qualiair.exception.FileNotFoundException;
import fr.diginamic.qualiair.repository.MessageRepository;
import fr.diginamic.qualiair.repository.RubriqueRepository;
import fr.diginamic.qualiair.repository.TopicRepository;
import org.springframework.stereotype.Component;

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
}
