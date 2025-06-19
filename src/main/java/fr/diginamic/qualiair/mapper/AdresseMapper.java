package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.entitesDto.AdresseDto;
import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.entity.Commune;

public interface AdresseMapper {
    /**
     * Convertit une entité Adresse en AdresseDto
     *
     * @param adresse l'entité à convertir
     * @return le DTO correspondant
     */
    AdresseDto toDto(Adresse adresse);

    Adresse fromDto(AdresseDto dto, Commune commune);
}
