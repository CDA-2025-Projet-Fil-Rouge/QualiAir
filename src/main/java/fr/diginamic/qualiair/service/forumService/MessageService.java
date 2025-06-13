package fr.diginamic.qualiair.service.forumService;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.entity.Message;
import fr.diginamic.qualiair.entity.RoleUtilisateur;
import fr.diginamic.qualiair.entity.Topic;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.mapper.forumMapper.MessageMapper;
import fr.diginamic.qualiair.repository.MessageRepository;
import fr.diginamic.qualiair.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    private TopicRepository topicRepository;

    public List<MessageDto> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(message -> messageMapper.toDto(message))
                .toList();
    }

    public MessageDto createMessage(MessageDto dto, Utilisateur createur) {
        if (dto.getContenu() == null || dto.getContenu().trim().isEmpty()) {
            throw new IllegalArgumentException("Le contenu du message ne peut pas être vide.");
        }
        Topic topic = topicRepository.findById(dto.getIdTopic())
                .orElseThrow(() -> new IllegalArgumentException("Topic introuvable"));

        Message message = messageMapper.toEntity(dto);
        message.setCreateur(createur);
        message.setDateCreation(LocalDateTime.now());
        message.setTopic(topic);
        messageRepository.save(message);
        return messageMapper.toDto(message);
    }

    public MessageDto updateMessage(Long idMessage, MessageDto dto, Utilisateur modificateur) {
        Message message = messageRepository.findById(idMessage)
                .orElseThrow(() -> new IllegalArgumentException("Message introuvable"));

        if (!(modificateur.getRole().equals(RoleUtilisateur.ADMIN)
                || modificateur.getId().equals(message.getCreateur().getId()))) {
            throw new AccessDeniedException("Seul un administrateur ou le créateur peut modifier ce message.");
        }

        if(dto.getContenu() != null && !dto.getContenu().trim().isEmpty()) {
            message.setContenu(dto.getContenu());
            message.setDateModification(LocalDateTime.now());
            message.setModificateur(modificateur);
        } else {
            throw new IllegalArgumentException("Le contenu du message ne peut pas être vide");
        }
        messageRepository.save(message);
        return messageMapper.toDto(message);
    }
}
