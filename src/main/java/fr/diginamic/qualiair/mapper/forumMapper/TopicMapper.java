package fr.diginamic.qualiair.mapper.forumMapper;

import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.entity.Topic;
import org.springframework.stereotype.Component;

/**
 * Mapper permettant de convertir réciproquement les entités Topic et TopicDto.
 * Utilisé pour isoler la logique de transformation dans la couche service.
 */
@Component
public class TopicMapper {

    /**
     * Convertit une entité Topic en TopicDto
     *
     * @param topic l'entité à convertir
     * @return le DTO correspondant
     */
    public TopicDto toDto(Topic topic) {
        TopicDto dto = new TopicDto();
        dto.setId(topic.getId());
        dto.setNom(topic.getNom());
        dto.setDateCreation(topic.getDateCreation());
        dto.setIdCreateur(topic.getCreateur().getId());
        dto.setIdRubrique(topic.getRubrique().getId());
        if (topic.getModificateur() != null) {
            dto.setIdModificateur(topic.getModificateur().getId());
            dto.setDateModification(topic.getDateModification());
        }
        return dto;
    }

    /**
     * Convertit un TopicDto en entité Topic.
     * Ne renseigne que les champs transmis depuis le client.
     *
     * @param dto le DTO source
     * @return l'entité partiellement construite
     */
    public Topic toEntity(TopicDto dto) {
        Topic entity = new Topic();
        entity.setNom(dto.getNom());
        return entity;
    }
}
