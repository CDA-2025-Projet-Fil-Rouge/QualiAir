package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.carte.InfoCarteCommune;
import fr.diginamic.qualiair.dto.favoris.InfoFavorite;
import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.entity.Commune;

public interface CommuneMapper {
    /**
     * Map a dto from csv to entity
     *
     * @param dto dto from csv
     * @return entity
     */
    Commune toEntityFromCommuneCoordDto(CommuneCoordDto dto);

    InfoCarteCommune toDto(Commune commune);

    InfoFavorite toDto(Commune commune, Long userId);
}
