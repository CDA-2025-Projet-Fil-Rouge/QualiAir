package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.atmofrance.AirDataFeatureDto;
import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.exception.ParsedDataException;

public interface CoordonneeMapper {
    /**
     * Map a dto from csv to entity
     *
     * @param dto dto from csv
     * @return entity
     */
    Coordonnee toEntityFromCommuneCoordDto(CommuneCoordDto dto) throws ParsedDataException;

    Coordonnee toEntityFromAirDataTo(AirDataFeatureDto feature) throws ParsedDataException;
}
