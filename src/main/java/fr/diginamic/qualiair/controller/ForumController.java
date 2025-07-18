package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.entity.forum.ReactionType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ForumController {
    /**
     * Retourne la liste paginée de toutes les rubriques existantes
     *
     * @param page numéro de la page (défaut : 0)
     * @param size taille de la page (défaut : 5)
     * @return page de RubriqueDto
     */
    @GetMapping("/rubrique/get-all")
    ResponseEntity<Page<RubriqueDto>> getAllRubriques(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    );

    /**
     * Retourne toutes les rubriques existantes
     * @return la liste dto correspondant aux rubriques
     */
    @GetMapping("rubrique/all")
    ResponseEntity<List<RubriqueDto>> getAllRubriquesUnpaged();

    /**
     * Retourne la liste paginée de tous les topics existants
     *
     * @param page numéro de la page (défaut : 0)
     * @param size taille de la page (défaut : 5)
     * @return page de TopicDto
     */
    @GetMapping("/topic/get-all")
    ResponseEntity<Page<TopicDto>> getAllTopics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    );

    /**
     * Récupère et affiche tous les topics existants d'une rubrique
     *
     * @param idRubrique désigne l'id de la Rubrique parente
     * @return la liste des topics associés à la rubrique indiquée
     */
    @GetMapping("/topic/by-rubrique/{idRubrique}")
    List<TopicDto> getTopicsByRubrique(@PathVariable Long idRubrique);

    /**
     * Récupère un topic à partir de son id
     * @param id désigne l'identifiant du topic à retrouver
     * @return le topic concerné
     * @throws Exception si le topic recherché n'est pas trouvé.
     */
    @GetMapping("/topic/by-id/{id}")
    ResponseEntity<TopicDto> getTopicById(@PathVariable Long id) throws Exception;

    /**
     * Retourne la liste paginée de tous les messages existants
     *
     * @param page numéro de la page (défaut : 0)
     * @param size taille de la page (défaut : 10)
     * @return page de MessageDto
     */
    @GetMapping("/message/get-all")
    ResponseEntity<Page<MessageDto>> getAllMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    /**
     * Récupère et affiche tous les messages existants d'un topic
     *
     * @param idTopic désigne l'id du topic parent
     * @return la liste des messages associés au topic indiqué
     */
    @GetMapping("/message/by-topic/{idTopic}")
    List<MessageDto> getMessagesByTopic(
            @PathVariable Long idTopic,
            HttpServletRequest request) throws Exception;

    /**
     * Crée une nouvelle rubrique à partir des données fournies dans le corps de la requête.
     *
     * @param dto     les données de la rubrique à créer
     * @param request la requête HTTP contenant le cookie JWT pour authentification
     * @return la rubrique créée
     * @throws Exception si l'utilisateur est introuvable ou non autorisé
     */
    @PostMapping("/create-rubrique")
    ResponseEntity<RubriqueDto> createRubrique(
            @RequestBody RubriqueDto dto,
            HttpServletRequest request) throws Exception;

    /**
     * Crée un nouveau topic à partir des données fournies dans le corps de la requête.
     *
     * @param dto     les données du topic à créer
     * @param request la requête HTTP contenant le cookie JWT pour authentification
     * @return le topic créé
     * @throws Exception si l'utilisateur est introuvable ou non autorisé
     */
    @PostMapping("/create-topic")
    ResponseEntity<TopicDto> createTopic(
            @RequestBody TopicDto dto,
            HttpServletRequest request) throws Exception;

    /**
     * Crée un nouveau message à partir des données fournies dans le corps de la requête.
     *
     * @param dto     les données du message à créer
     * @param request la requête HTTP contenant le cookie JWT pour authentification
     * @return le message créé
     * @throws Exception si l'utilisateur est introuvable ou non autorisé
     */
    @PostMapping("/create-message")
    ResponseEntity<MessageDto> createMessage(
            @RequestBody MessageDto dto,
            HttpServletRequest request) throws Exception;

    /**
     * Incrémente l'attribut de message correspondant à la réaction effectuée par un utilisateur
     *
     * @param id      identifiant du message
     * @param request la requête HTTP contenant le cookie JWT pour authentification
     * @return la confirmation contenant le message auquel l'utilisateur a réagi
     * @throws Exception si une erreur est rencontrée
     */
    @PostMapping("/message/{id}/react")
    ResponseEntity<MessageDto> react(
            @PathVariable Long id,
            @RequestParam ReactionType type,
            HttpServletRequest request) throws Exception;

    /**
     * Met à jour une rubrique existante, uniquement si l'utilisateur est admin.
     *
     * @param id      l'identifiant de la rubrique à mettre à jour
     * @param dto     les nouvelles données de la rubrique
     * @param request la requête HTTP contenant l'identité de l'utilisateur
     * @return la rubrique mise à jour
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @PutMapping("/update-rubrique/{id}")
    ResponseEntity<RubriqueDto> updateRubrique(
            @PathVariable Long id,
            @RequestBody RubriqueDto dto,
            HttpServletRequest request) throws Exception;

    /**
     * Met à jour un topic existant, uniquement si l'utilisateur est son auteur ou un administrateur.
     *
     * @param id      l'identifiant du topic à mettre à jour
     * @param dto     les nouvelles données du topic
     * @param request la requête HTTP contenant l'identité de l'utilisateur
     * @return le topic mis à jour
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @PutMapping("/update-topic/{id}")
    ResponseEntity<TopicDto> updateTopic(
            @PathVariable Long id,
            @RequestBody TopicDto dto,
            HttpServletRequest request) throws Exception;

    /**
     * Met à jour un message existant, uniquement si l'utilisateur est son auteur ou un administrateur.
     *
     * @param id      l'identifiant du message à mettre à jour
     * @param dto     les nouvelles données du message
     * @param request la requête HTTP contenant l'identité de l'utilisateur
     * @return le message mis à jour
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @PutMapping("/update-message/{id}")
    ResponseEntity<MessageDto> updateMessage(
            @PathVariable Long id,
            @RequestBody MessageDto dto,
            HttpServletRequest request) throws Exception;

    /**
     * Supprime une rubrique existante, uniquement si l'utilisateur est admin et si la rubrique ne contient pas de topic
     *
     * @param id      désigne l'id de la rubrique à supprimer
     * @param request la requête HTTP contenant l'identité de l'utilisateur
     * @return le message de confirmation si la suppression s'est bien passée
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @DeleteMapping("/delete-rubrique/{id}")
    ResponseEntity<String> deleteRubrique(
            @PathVariable Long id,
            HttpServletRequest request) throws Exception;

    /**
     * Supprime un topic existant, uniquement si l'utilisateur est admin et si le topic ne contient pas de message
     *
     * @param id      désigne l'id du topic à supprimer
     * @param request la requête HTTP contenant l'identité de l'utilisateur
     * @return le message de confirmation si la suppression s'est bien passée
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @DeleteMapping("/delete-topic/{id}")
    ResponseEntity<String> deleteTopic(
            @PathVariable Long id,
            HttpServletRequest request) throws Exception;

    /**
     * Supprime un message existant, uniquement si l'utilisateur est son auteur ou un administrateur.
     *
     * @param id      désigne l'id du message à supprimer
     * @param request la requête HTTP contenant l'identité de l'utilisateur
     * @return le message de confirmation si la suppression s'est bien passée
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @DeleteMapping("/delete-message/{id}")
    ResponseEntity<?> deleteMessage(
            @PathVariable Long id,
            HttpServletRequest request
    ) throws Exception;

    /**
     * Retire une réaction préalablement effectuée à un message du forum
     *
     * @param id      identifiant du message à l'origine de la réaction
     * @param type    type de réaction à retirer (like, dislike, report)
     * @param request la requête HTTP contenant l'identité de l'utilisateur
     * @return la confirmation contenant le message duquel la réaction a été annulée
     * @throws Exception si une erreur est rencontrée
     */
    @DeleteMapping("/message/{id}/unreact")
    ResponseEntity<MessageDto> removeReaction(
            @PathVariable Long id,
            @RequestParam ReactionType type,
            HttpServletRequest request) throws Exception;
}
