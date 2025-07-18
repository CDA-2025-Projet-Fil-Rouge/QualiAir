package fr.diginamic.qualiair.mapper.forumMapper;

import fr.diginamic.qualiair.dto.forumDto.TopicDto;
import fr.diginamic.qualiair.entity.forum.Topic;
import fr.diginamic.qualiair.mapper.UtilisateurMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper permettant de convertir réciproquement les entités Topic et TopicDto.
 * Utilisé pour isoler la logique de transformation dans la couche service.
 */
@Component
public class TopicMapperImpl implements TopicMapper {

    @Autowired
    UtilisateurMapper utilisateurMapper;

    @Override
    public TopicDto toDto(Topic topic) {
        TopicDto dto = new TopicDto();
        dto.setId(topic.getId());
        dto.setNom(topic.getNom());
        dto.setDateCreation(topic.getDateCreation());
        dto.setCreateur(utilisateurMapper.toDto(topic.getCreateur()));
        dto.setIdRubrique(topic.getRubrique().getId());
        if (topic.getModificateur() != null) {
            dto.setModificateur(utilisateurMapper.toDto(topic.getModificateur()));
            dto.setDateModification(topic.getDateModification());
        }
        return dto;
    }

    @Override
    public Topic toEntity(TopicDto dto) {
        Topic entity = new Topic();
        entity.setNom(dto.getNom());
        return entity;
    }
}
