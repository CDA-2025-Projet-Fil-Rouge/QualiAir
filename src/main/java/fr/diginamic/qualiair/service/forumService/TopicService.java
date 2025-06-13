package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.entity.Rubrique;
import fr.diginamic.qualiair.entity.Topic;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.mapper.forumMapper.TopicMapper;
import fr.diginamic.qualiair.repository.RubriqueRepository;
import fr.diginamic.qualiair.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    private RubriqueRepository rubriqueRepository;

    public List<TopicDto> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(topic -> topicMapper.toDto(topic))
                .toList();
    }

    public TopicDto createTopic(TopicDto dto, Utilisateur createur) {
        Topic topic = topicMapper.toEntity(dto);
        topic.setCreateur(createur);
        topic.setDateCreation(LocalDateTime.now());

        Rubrique rubrique = rubriqueRepository.findById(dto.getIdRubrique())
                        .orElseThrow(() -> new IllegalArgumentException("Rubrique introuvable"));
        topic.setRubrique(rubrique);
        topicRepository.save(topic);
        return topicMapper.toDto(topic);
    }
}
