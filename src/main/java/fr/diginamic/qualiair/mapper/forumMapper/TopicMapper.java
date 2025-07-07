package fr.diginamic.qualiair.mapper.forumMapper;

import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.entity.forum.Topic;

public interface TopicMapper {
    /**
     * Convertit une entité Topic en TopicDto
     *
     * @param topic l'entité à convertir
     * @return le DTO correspondant
     */
    TopicDto toDto(Topic topic);

    /**
     * Convertit un TopicDto en entité Topic.
     * Ne renseigne que les champs transmis depuis le client.
     *
     * @param dto le DTO source
     * @return l'entité partiellement construite
     */
    Topic toEntity(TopicDto dto);
}
