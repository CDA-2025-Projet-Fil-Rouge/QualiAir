package fr.diginamic.qualiair.mapper.forumMapper;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper{

    public MessageDto toDto(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setContenu(message.getContenu());
        messageDto.setDateCreation(message.getDateCreation());
        messageDto.setIdCreateur(message.getCreateur().getId());
        messageDto.setIdTopic(message.getTopic().getId());
        if(message.getModificateur() != null) {
            messageDto.setIdModificateur(message.getModificateur().getId());
            messageDto.setDateModification(message.getDateModification());
        }
        return messageDto;
    }

    public Message toEntity(MessageDto dto) {
        Message entity = new Message();
        entity.setContenu(dto.getContenu());
        return entity;
    }
}
