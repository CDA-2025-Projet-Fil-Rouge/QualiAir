package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.entitesDto.AdresseDto;
import fr.diginamic.qualiair.entity.Adresse;
import org.springframework.stereotype.Component;

@Component
public class AdresseMapper {

    /**
     * Convertit une entité Adresse en AdresseDto
     *
     * @param adresse l'entité à convertir
     * @return le DTO correspondant
     */
    public AdresseDto toDto(Adresse adresse) {
        AdresseDto dto = new AdresseDto();
        dto.setId(adresse.getId());
        dto.setNumeroRue(adresse.getNumeroRue());
        dto.setLibelleRue(adresse.getLibelleRue());
        dto.setCodePostal(adresse.getCommune().getCodePostal());
        dto.setNomCommune(adresse.getCommune().getNomComplet());
        return dto;
    }
}
