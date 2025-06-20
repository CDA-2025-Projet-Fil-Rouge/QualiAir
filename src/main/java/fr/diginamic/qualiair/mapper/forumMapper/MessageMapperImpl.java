package fr.diginamic.qualiair.mapper.forumMapper;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.entity.forum.Message;
import org.springframework.stereotype.Component;

/**
 * Mapper permettant de convertir réciproquement les entités Message et MessageDto.
 * Utilisé pour isoler la logique de transformation dans la couche service.
 */
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageDto toDto(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setContenu(message.getContenu());
        messageDto.setDateCreation(message.getDateCreation());
        messageDto.setIdCreateur(message.getCreateur().getId());
        messageDto.setIdTopic(message.getTopic().getId());
        messageDto.setNbLike(message.getNbLike());
        messageDto.setNbDislike(message.getNbDislike());
        messageDto.setNbSignalement(message.getNbSignalement());
        if (message.getModificateur() != null) {
            messageDto.setIdModificateur(message.getModificateur().getId());
            messageDto.setDateModification(message.getDateModification());
        }
        return messageDto;
    }

    @Override
    public Message toEntity(MessageDto dto) {
        Message entity = new Message();
        entity.setContenu(dto.getContenu());
        return entity;
    }
}
