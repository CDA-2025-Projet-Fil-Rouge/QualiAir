package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.entity.Message;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.security.JwtAuthentificationService;
import fr.diginamic.qualiair.service.UtilisateurService;
import fr.diginamic.qualiair.service.forumService.MessageService;
import fr.diginamic.qualiair.service.forumService.RubriqueService;
import fr.diginamic.qualiair.service.forumService.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    RubriqueService rubriqueService;
    @Autowired
    TopicService topicService;
    @Autowired
    MessageService messageService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private JwtAuthentificationService jwtAuthentificationService;

    // Pour vérifier la création
    @GetMapping
    public Map<String, Object> forumSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("rubriques", rubriqueService.getAllRubriques().size());
        summary.put("topics", topicService.getAllTopics().size());
        summary.put("messages", messageService.getAllMessages().size());
        return summary;
    }

    @GetMapping("/rubrique/get-all")
    public List<RubriqueDto> getAllRubriques() {
        return rubriqueService.getAllRubriques();
    }

    @GetMapping("/topic/get-all")
    public List<TopicDto> getAllTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping("message/get-all")
    public List<MessageDto> getAllMessages() {
        return messageService.getAllMessages();
    }

    @PostMapping("/create-rubrique")
    public ResponseEntity<RubriqueDto> createRubrique(
            @RequestBody RubriqueDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur createur = utilisateurService.getUser(
                jwtAuthentificationService.getEmailFromCookie(request));
        RubriqueDto created = rubriqueService.createRubrique(dto, createur);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/create-topic")
    public ResponseEntity<TopicDto> createTopic(
            @RequestBody TopicDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur createur = utilisateurService.getUser(
                jwtAuthentificationService.getEmailFromCookie(request));
        TopicDto created = topicService.createTopic(dto, createur);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/create-message")
    public ResponseEntity<MessageDto> createMessage(
            @RequestBody MessageDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur createur = utilisateurService.getUser(
                jwtAuthentificationService.getEmailFromCookie(request));
        MessageDto created = messageService.createMessage(dto, createur);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/update-message/{id}")
    public ResponseEntity<MessageDto> updateMessage(
            @PathVariable Long id,
            @RequestBody MessageDto dto,
            HttpServletRequest request) throws Exception {
        Utilisateur modificateur = utilisateurService.getUser(
                jwtAuthentificationService.getEmailFromCookie(request));
        MessageDto updated = messageService.updateMessage(id, dto, modificateur);
        return ResponseEntity.ok(updated);
    }
 }

