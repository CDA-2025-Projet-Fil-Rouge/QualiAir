package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.entity.forum.ReactionType;
import fr.diginamic.qualiair.service.forumService.MessageService;
import fr.diginamic.qualiair.service.forumService.RubriqueService;
import fr.diginamic.qualiair.service.forumService.TopicService;
import fr.diginamic.qualiair.utils.HttpRequestUtils;
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
public class ForumControllerImpl implements ForumController {

    @Autowired
    private RubriqueService rubriqueService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private HttpRequestUtils httpRequestUtils;

    @GetMapping("/rubrique/get-all")
    @Override
    public ResponseEntity<Page<RubriqueDto>> getAllRubriques(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "prioriteAffichageIndice"));
        return ResponseEntity.ok(rubriqueService.getAllRubriques(pageable));
    }

    @GetMapping("/topic/get-all")
    @Override
    public ResponseEntity<Page<TopicDto>> getAllTopics(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreation").ascending());
        return ResponseEntity.ok(topicService.getAllTopics(pageable));
    }

    @GetMapping("/topic/by-rubrique/{idRubrique}")
    @Override
    public List<TopicDto> getTopicsByRubrique(@PathVariable Long idRubrique) {
        return topicService.getTopicsByRubrique(idRubrique);
    }

    @GetMapping("/message/get-all")
    @Override
    public ResponseEntity<Page<MessageDto>> getAllMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateCreation").ascending());
        return ResponseEntity.ok(messageService.getAllMessages(pageable));
    }

    @GetMapping("/message/by-topic/{idTopic}")
    @Override
    public List<MessageDto> getMessagesByTopic(@PathVariable Long idTopic) {
        return messageService.getMessagesByTopic(idTopic);
    }

    @PostMapping("/create-rubrique")
    @Override
    public ResponseEntity<RubriqueDto> createRubrique(
            @RequestBody RubriqueDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur createur = httpRequestUtils.getUtilisateurFromRequest(request);
        RubriqueDto created = rubriqueService.createRubrique(dto, createur);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/create-topic")
    @Override
    public ResponseEntity<TopicDto> createTopic(
            @RequestBody TopicDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur createur = httpRequestUtils.getUtilisateurFromRequest(request);
        TopicDto created = topicService.createTopic(dto, createur);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/create-message")
    @Override
    public ResponseEntity<MessageDto> createMessage(
            @RequestBody MessageDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur createur = httpRequestUtils.getUtilisateurFromRequest(request);
        MessageDto created = messageService.createMessage(dto, createur);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/message/{id}/react")
    @Override
    public ResponseEntity<MessageDto> react(
            @PathVariable Long id,
            @RequestParam ReactionType type,
            HttpServletRequest request) throws Exception {
        Utilisateur user = httpRequestUtils.getUtilisateurFromRequest(request);
        return ResponseEntity.ok(messageService.reactToMessage(id, user, type));
    }

    @PutMapping("/update-rubrique/{id}")
    @Override
    public ResponseEntity<RubriqueDto> updateRubrique(
            @PathVariable Long id,
            @RequestBody RubriqueDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur modificateur = httpRequestUtils.getUtilisateurFromRequest(request);
        RubriqueDto updated = rubriqueService.updateRubrique(id, dto, modificateur);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/update-topic/{id}")
    @Override
    public ResponseEntity<TopicDto> updateTopic(
            @PathVariable Long id,
            @RequestBody TopicDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur modificateur = httpRequestUtils.getUtilisateurFromRequest(request);
        TopicDto updated = topicService.updateTopic(id, dto, modificateur);
        return ResponseEntity.ok(updated);
    }


    @PutMapping("/update-message/{id}")
    @Override
    public ResponseEntity<MessageDto> updateMessage(
            @PathVariable Long id,
            @RequestBody MessageDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur modificateur = httpRequestUtils.getUtilisateurFromRequest(request);
        MessageDto updated = messageService.updateMessage(id, dto, modificateur);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete-rubrique/{id}")
    @Override
    public ResponseEntity<String> deleteRubrique(
            @PathVariable Long id,
            HttpServletRequest request) throws Exception {
        Utilisateur user = httpRequestUtils.getUtilisateurFromRequest(request);
        rubriqueService.deleteRubrique(id, user);
        return ResponseEntity.ok("Rubrique supprimée");
    }

    @DeleteMapping("/delete-topic/{id}")
    @Override
    public ResponseEntity<String> deleteTopic(
            @PathVariable Long id,
            HttpServletRequest request) throws Exception {
        Utilisateur user = httpRequestUtils.getUtilisateurFromRequest(request);
        topicService.deleteTopic(id, user);
        return ResponseEntity.ok("Topic supprimé");
    }

    @DeleteMapping("/delete-message/{id}")
    @Override
    public ResponseEntity<?> deleteMessage(
            @PathVariable Long id,
            HttpServletRequest request
    ) throws Exception {
        Utilisateur user = httpRequestUtils.getUtilisateurFromRequest(request);
        messageService.deleteMessage(id, user);
        return ResponseEntity.ok("Message supprimé");
    }

    @DeleteMapping("/message/{id}/unreact")
    @Override
    public ResponseEntity<MessageDto> removeReaction(
            @PathVariable Long id,
            @RequestParam ReactionType type,
            HttpServletRequest request) throws Exception {
        Utilisateur user = httpRequestUtils.getUtilisateurFromRequest(request);
        return ResponseEntity.ok(messageService.removeReaction(id, user, type));
    }
}
