package fr.diginamic.qualiair.controller;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.service.forumService.MessageService;
import fr.diginamic.qualiair.service.forumService.RubriqueService;
import fr.diginamic.qualiair.service.forumService.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/forum")
public class ForumController {

    @Autowired
    RubriqueService rubriqueService;
    @Autowired
    TopicService topicService;
    @Autowired
    MessageService messageService;

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
}
