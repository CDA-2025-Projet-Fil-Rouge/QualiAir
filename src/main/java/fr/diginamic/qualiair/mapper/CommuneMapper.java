package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.entity.Commune;
import fr.diginamic.qualiair.exception.ParsedDataException;
import org.springframework.stereotype.Component;

import static fr.diginamic.qualiair.utils.CommuneUtils.toInt;

/**
 * Mapper for Commune
 */
@Component
public class CommuneMapper {

    /**
     * Map a dto from csv to entity
     *
     * @param dto dto from csv
     * @return entity
     */
    public Commune toEntityFromCommuneCoordDto(CommuneCoordDto dto) throws ParsedDataException {
        Commune commune = new Commune();

        commune.setNomPostal(dto.getNomCommunePostal());
        commune.setNomComplet(dto.getNomCommuneComplet());
        commune.setCodeInsee(dto.getCodeCommuneINSEE());
        commune.setCodePostal(toInt(dto.getCodePostal()));
        return commune;
    }

    public Commune toEntityFromCommuneHabitantDto(CommuneHabitantDto dto) {
        return null; // todo if needed
    }
}
