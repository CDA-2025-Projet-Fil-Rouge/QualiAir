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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Pour vérifier la création
    @GetMapping
    public Map<String, Object> forumSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("rubriques", rubriqueService.getAllRubriques().size());
        summary.put("topics", topicService.getAllTopics().size());
        summary.put("messages", messageService.getAllMessages().size());
        return summary;
    }

    /**
     * Récupère et affiche toutes les rubriques existantes
     * @return la liste des rubriques
     */
    @GetMapping("/rubrique/get-all")
    public List<RubriqueDto> getAllRubriques() {
        return rubriqueService.getAllRubriques();
    }

    /**
     * Récupère et affiche tous les topics existants
     * @return la liste des topics
     */
    @GetMapping("/topic/get-all")
    public List<TopicDto> getAllTopics() {
        return topicService.getAllTopics();
    }

    /**
     * Récupère et affiche tous les messages existants
     * @return la liste des messages
     */
    @GetMapping("message/get-all")
    public List<MessageDto> getAllMessages() {
        return messageService.getAllMessages();
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
}

