package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.carte.FiveDaysForecastView;
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

    InfoCarteCommune toMapDataView(Commune commune);

    InfoFavorite toMapDataView(Commune commune, Long userId);

    FiveDaysForecastView toForecastView(Commune commune);

    Object toDto(Commune commune);
}
