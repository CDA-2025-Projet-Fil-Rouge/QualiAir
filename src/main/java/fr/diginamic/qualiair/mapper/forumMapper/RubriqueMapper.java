package fr.diginamic.qualiair.mapper.forumMapper;

import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.entity.Rubrique;
import org.springframework.stereotype.Component;

@Component
public class RubriqueMapper {

    public RubriqueDto toDto(Rubrique rubrique) {
        RubriqueDto dto = new RubriqueDto();
        dto.setId(rubrique.getId());
        dto.setNom(rubrique.getNom());
        dto.setDescription(rubrique.getDescription());
        dto.setPrioriteAffichageIndice(rubrique.getPrioriteAffichageIndice());
        dto.setDateCreation(rubrique.getDateCreation());
        dto.setIdCreateur(rubrique.getCreateur().getId());
        return dto;
    }
}
