package fr.diginamic.qualiair.mapper.forumMapper;

import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.entity.forum.Rubrique;
import org.springframework.stereotype.Component;

/**
 * Mapper permettant de convertir réciproquement les entités Rubrique et RubriqueDto.
 * Utilisé pour isoler la logique de transformation dans la couche service.
 */
@Component
public class RubriqueMapperImpl implements RubriqueMapper {

    @Override
    public RubriqueDto toDto(Rubrique rubrique) {
        RubriqueDto dto = new RubriqueDto();
        dto.setId(rubrique.getId());
        dto.setNom(rubrique.getNom());
        dto.setDescription(rubrique.getDescription());
        dto.setPrioriteAffichageIndice(rubrique.getPrioriteAffichageIndice());
        dto.setDateCreation(rubrique.getDateCreation());
        dto.setIdCreateur(rubrique.getCreateur().getId());
        if (rubrique.getModificateur() != null) {
            dto.setIdModificateur(rubrique.getModificateur().getId());
            dto.setDateModification(rubrique.getDateModification());
        }
        return dto;
    }

    @Override
    public Rubrique toEntity(RubriqueDto dto) {
        Rubrique entity = new Rubrique();
        entity.setNom(dto.getNom());
        entity.setDescription(dto.getDescription());
        entity.setPrioriteAffichageIndice(dto.getPrioriteAffichageIndice());
        return entity;
    }
}
