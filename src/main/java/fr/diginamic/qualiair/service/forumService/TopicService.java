package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.mapper.forumMapper.TopicMapper;
import fr.diginamic.qualiair.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    TopicRepository topicRepository;
    @Autowired
    TopicMapper topicMapper;

    public List<TopicDto> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(topic -> topicMapper.toDto(topic))
                .toList();
    }
}
