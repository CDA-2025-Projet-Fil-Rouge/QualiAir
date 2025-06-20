package fr.diginamic.qualiair.mapper.forumMapper;

import fr.diginamic.qualiair.dto.forumDto.RubriqueDto;
import fr.diginamic.qualiair.entity.forum.Rubrique;

public interface RubriqueMapper {
    /**
     * Convertit une entité Rubrique en RubriqueDto
     *
     * @param rubrique l'entité à convertir
     * @return le DTO correspondant
     */
    RubriqueDto toDto(Rubrique rubrique);

    /**
     * Convertit une RubriqueDto en entité Rubrique.
     * Ne renseigne que les champs transmis depuis le client.
     *
     * @param dto le DTO source
     * @return l'entité partiellement construite
     */
    Rubrique toEntity(RubriqueDto dto);
}
