package fr.diginamic.qualiair.mapper.forumMapper;

import fr.diginamic.qualiair.dto.forumDto.MessageDto;
import fr.diginamic.qualiair.entity.forum.Message;

public interface MessageMapper {
    /**
     * Convertit une entité Message en MessageDto
     *
     * @param message l'entité à convertir
     * @return le DTO correspondant
     */
    MessageDto toDto(Message message);

    /**
     * Convertit un MessageDto en entité Message.
     * Ne renseigne que les champs transmis depuis le client.
     *
     * @param dto le DTO source
     * @return l'entité partiellement construite
     */
    Message toEntity(MessageDto dto);
}
