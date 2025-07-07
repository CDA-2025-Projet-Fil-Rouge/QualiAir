package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.RegionDto;
import fr.diginamic.qualiair.entity.Region;

public interface RegionMapper {
    /**
     * Map a dto from csv to entity
     *
     * @param dto dto from csv
     * @return entity
     */


    Region toEntity(RegionDto dto);
}
