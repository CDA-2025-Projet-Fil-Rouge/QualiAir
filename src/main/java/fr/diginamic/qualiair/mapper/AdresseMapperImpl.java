package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.entitesDto.AdresseDto;
import fr.diginamic.qualiair.entity.Adresse;
import fr.diginamic.qualiair.entity.Commune;
import org.springframework.stereotype.Component;

@Component
public class AdresseMapperImpl implements AdresseMapper {

    @Override
    public AdresseDto toDto(Adresse adresse) {
        AdresseDto dto = new AdresseDto();
        dto.setNumeroRue(adresse.getNumeroRue());
        dto.setLibelleRue(adresse.getLibelleRue());
        dto.setCodePostal(adresse.getCommune().getCodePostal());
        dto.setNomCommune(adresse.getCommune().getNomReel());
        return dto;
    }

    @Override
    public Adresse fromDto(AdresseDto dto, Commune commune) {
        Adresse adresse = new Adresse();
        adresse.setNumeroRue(dto.getNumeroRue());
        adresse.setLibelleRue(dto.getLibelleRue());
        adresse.setCommune(commune);
        return adresse;
    }
}
