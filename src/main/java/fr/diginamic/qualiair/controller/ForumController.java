package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.service.forumService.MessageService;
import fr.diginamic.qualiair.service.forumService.RubriqueService;
import fr.diginamic.qualiair.service.forumService.TopicService;
import fr.diginamic.qualiair.utils.api.HttpRequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion du forum.
 * Fournit des endpoints pour consulter, créer et modifier des rubriques, topics et messages.
 */
@RestController
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    private RubriqueService rubriqueService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private HttpRequestUtils httpRequestUtils;

    /**
     * Récupère et affiche toutes les rubriques existantes
     * @return la liste des rubriques
     */
    @GetMapping("/rubrique/get-all")
    public ResponseEntity<Page<RubriqueDto>> getAllRubriques(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "prioriteAffichageIndice"));
        return ResponseEntity.ok(rubriqueService.getAllRubriques(pageable));
    }

    /**
     * Récupère et affiche tous les topics existants
     * @return la liste des topics
     */
    @GetMapping("/topic/get-all")
    public ResponseEntity<Page<TopicDto>> getAllTopics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreation").ascending());
        return ResponseEntity.ok(topicService.getAllTopics(pageable));
    }

    /**
     * Récupère et affiche tous les topics existants d'une rubrique
     * @param idRubrique désigne l'id de la Rubrique parente
     * @return la liste des topics associés à la rubrique indiquée
     */
    @GetMapping("/topic/by-rubrique/{idRubrique}")
    public List<TopicDto> getTopicsByRubrique(@PathVariable Long idRubrique) {
        return topicService.getTopicsByRubrique(idRubrique);
    }

    /**
     * Récupère et affiche tous les messages existants
     * @return la liste des messages
     */
    @GetMapping("message/get-all")
    public ResponseEntity<Page<MessageDto>> getAllMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreation").ascending());
        return ResponseEntity.ok(messageService.getAllMessages(pageable));
    }

    /**
     * Récupère et affiche tous les messages existants d'un topic
     * @param idTopic désigne l'id du topic parent
     * @return la liste des messages associés au topic indiqué
     */
    @GetMapping("/message/by-topic/{idTopic}")
    public List<MessageDto> getMessagesByTopic(@PathVariable Long idTopic) {
        return messageService.getMessagesByTopic(idTopic);
    }

    /**
     * Crée une nouvelle rubrique à partir des données fournies dans le corps de la requête.
     *
     * @param dto     les données de la rubrique à créer
     * @param request la requête HTTP contenant le cookie JWT pour authentification
     * @return la rubrique créée
     * @throws Exception si l'utilisateur est introuvable ou non autorisé
     */
    @PostMapping("/create-rubrique")
    public ResponseEntity<RubriqueDto> createRubrique(
            @RequestBody RubriqueDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur createur = httpRequestUtils.getUtilisateurFromRequest(request);
        RubriqueDto created = rubriqueService.createRubrique(dto, createur);
        return ResponseEntity.ok(created);
    }

    /**
     * Crée un nouveau topic à partir des données fournies dans le corps de la requête.
     *
     * @param dto     les données du topic à créer
     * @param request la requête HTTP contenant le cookie JWT pour authentification
     * @return le topic créé
     * @throws Exception si l'utilisateur est introuvable ou non autorisé
     */
    @PostMapping("/create-topic")
    public ResponseEntity<TopicDto> createTopic(
            @RequestBody TopicDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur createur = httpRequestUtils.getUtilisateurFromRequest(request);
        TopicDto created = topicService.createTopic(dto, createur);
        return ResponseEntity.ok(created);
    }

    /**
     * Crée un nouveau message à partir des données fournies dans le corps de la requête.
     *
     * @param dto     les données du message à créer
     * @param request la requête HTTP contenant le cookie JWT pour authentification
     * @return le message créé
     * @throws Exception si l'utilisateur est introuvable ou non autorisé
     */
    @PostMapping("/create-message")
    public ResponseEntity<MessageDto> createMessage(
            @RequestBody MessageDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur createur = httpRequestUtils.getUtilisateurFromRequest(request);
        MessageDto created = messageService.createMessage(dto, createur);
        return ResponseEntity.ok(created);
    }

    /**
     * Met à jour une rubrique existante, uniquement si l'utilisateur est admin.
     *
     * @param id       l'identifiant de la rubrique à mettre à jour
     * @param dto      les nouvelles données de la rubrique
     * @param request  la requête HTTP contenant l'identité de l'utilisateur
     * @return la rubrique mise à jour
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @PutMapping("/update-rubrique/{id}")
    public ResponseEntity<RubriqueDto> updateRubrique(
            @PathVariable Long id,
            @RequestBody RubriqueDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur modificateur = httpRequestUtils.getUtilisateurFromRequest(request);
        RubriqueDto updated = rubriqueService.updateRubrique(id, dto, modificateur);
        return ResponseEntity.ok(updated);
    }

    /**
     * Met à jour un topic existant, uniquement si l'utilisateur est son auteur ou un administrateur.
     * @param id       l'identifiant du topic à mettre à jour
     * @param dto      les nouvelles données du topic
     * @param request  la requête HTTP contenant l'identité de l'utilisateur
     * @return le topic mis à jour
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @PutMapping("/update-topic/{id}")
    public ResponseEntity<TopicDto> updateTopic(
            @PathVariable Long id,
            @RequestBody TopicDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur modificateur = httpRequestUtils.getUtilisateurFromRequest(request);
        TopicDto updated = topicService.updateTopic(id, dto, modificateur);
        return ResponseEntity.ok(updated);
    }


    /**
     * Met à jour un message existant, uniquement si l'utilisateur est son auteur ou un administrateur.
     *
     * @param id       l'identifiant du message à mettre à jour
     * @param dto      les nouvelles données du message
     * @param request  la requête HTTP contenant l'identité de l'utilisateur
     * @return le message mis à jour
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @PutMapping("/update-message/{id}")
    public ResponseEntity<MessageDto> updateMessage(
            @PathVariable Long id,
            @RequestBody MessageDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur modificateur = httpRequestUtils.getUtilisateurFromRequest(request);
        MessageDto updated = messageService.updateMessage(id, dto, modificateur);
        return ResponseEntity.ok(updated);
    }

    /**
     * Supprime une rubrique existante, uniquement si l'utilisateur est admin et si la rubrique ne contient pas de topic
     * @param id désigne l'id de la rubrique à supprimer
     * @param request la requête HTTP contenant l'identité de l'utilisateur
     * @return le message de confirmation si la suppression s'est bien passée
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @DeleteMapping("/delete-rubrique/{id}")
    public ResponseEntity<String> deleteRubrique(
            @PathVariable Long id,
            HttpServletRequest request) throws Exception {
        Utilisateur user = httpRequestUtils.getUtilisateurFromRequest(request);
        rubriqueService.deleteRubrique(id, user);
        return ResponseEntity.ok("Rubrique supprimée");
    }

    /**
     * Supprime un topic existant, uniquement si l'utilisateur est admin et si le topic ne contient pas de message
     * @param id désigne l'id du topic à supprimer
     * @param request la requête HTTP contenant l'identité de l'utilisateur
     * @return le message de confirmation si la suppression s'est bien passée
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @DeleteMapping("/delete-topic/{id}")
    public ResponseEntity<String> deleteTopic(
            @PathVariable Long id,
            HttpServletRequest request) throws Exception {
        Utilisateur user = httpRequestUtils.getUtilisateurFromRequest(request);
        topicService.deleteTopic(id, user);
        return ResponseEntity.ok("Topic supprimé");
    }

    /**
     * Supprime un message existant, uniquement si l'utilisateur est son auteur ou un administrateur.
     * @param id désigne l'id du message à supprimer
     * @param request la requête HTTP contenant l'identité de l'utilisateur
     * @return le message de confirmation si la suppression s'est bien passée
     * @throws Exception si l'accès est interdit ou si des erreurs métier sont rencontrées
     */
    @DeleteMapping("/delete-message/{id}")
    public ResponseEntity<?> deleteMessage(
            @PathVariable Long id,
            HttpServletRequest request
    ) throws Exception {
        Utilisateur user = httpRequestUtils.getUtilisateurFromRequest(request);
        messageService.deleteMessage(id, user);
        return ResponseEntity.ok("Message supprimé");
    }
}

