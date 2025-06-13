package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.entity.Commune;
import org.springframework.stereotype.Component;

import static fr.diginamic.qualiair.utils.CommuneUtils.toInt;

@Component
public class CommuneMapper {

    public Commune toEntityFromCommuneCoordDto(CommuneCoordDto dto) {
        Commune commune = new Commune();

        commune.setNom(dto.getNomCommuneComplet());
        commune.setCodeInsee(dto.getCodeCommuneINSEE());
        commune.setCodePostal(toInt(dto.getCodePostal()));
        return commune;
    }

    public Commune toEntityFromCommuneHabitantDto(CommuneHabitantDto dto) {
        return null; // todo if needed
    }
}
