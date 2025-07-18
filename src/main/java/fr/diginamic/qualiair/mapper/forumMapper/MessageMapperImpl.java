package fr.diginamic.qualiair.mapper.forumMapper;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.entity.Utilisateur;
import fr.diginamic.qualiair.entity.forum.Message;
import fr.diginamic.qualiair.entity.forum.ReactionMessage;
import fr.diginamic.qualiair.entity.forum.ReactionType;
import fr.diginamic.qualiair.mapper.UtilisateurMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper permettant de convertir réciproquement les entités Message et MessageDto.
 * Utilisé pour isoler la logique de transformation dans la couche service.
 */
@Component
public class MessageMapperImpl implements MessageMapper {

    @Autowired
    UtilisateurMapper utilisateurMapper;

    @Override
    public MessageDto toDto(Message message) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setContenu(message.getContenu());
        messageDto.setDateCreation(message.getDateCreation());
        messageDto.setCreateur(utilisateurMapper.toDto(message.getCreateur()));
        messageDto.setIdTopic(message.getTopic().getId());
        messageDto.setNbLike(message.getNbLike());
        messageDto.setNbDislike(message.getNbDislike());
        messageDto.setNbSignalement(message.getNbSignalement());
        if (message.getModificateur() != null) {
            messageDto.setModificateur(utilisateurMapper.toDto(message.getModificateur()));
            messageDto.setDateModification(message.getDateModification());
        }
        return messageDto;
    }

    @Override
    public MessageDto toDto(Message message, Utilisateur utilisateur) {
        MessageDto dto = toDto(message);
        if (utilisateur != null) {
            List<ReactionType> types = message.getReactions().stream()
                    .filter(r -> r.getUtilisateur().getId().equals(utilisateur.getId()))
                    .map(ReactionMessage::getType)
                    .toList();

            dto.setUserReactions(types);
        }
        return dto;
    }

    @Override
    public Message toEntity(MessageDto dto) {
        Message entity = new Message();
        entity.setContenu(dto.getContenu());
        return entity;
    }
}
