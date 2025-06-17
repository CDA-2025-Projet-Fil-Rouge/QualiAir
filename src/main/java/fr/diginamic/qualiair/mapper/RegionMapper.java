package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.RegionDto;
import fr.diginamic.qualiair.entity.Region;
import org.springframework.stereotype.Component;

/**
 * Region mapper
 */
@Component
public class RegionMapper {
    /**
     * Map a dto from csv to entity
     *
     * @param dto dto from csv
     * @return entity
     */


    public Region toEntity(RegionDto dto) {
        Region region = new Region();
        region.setCode(Integer.parseInt(dto.getCodeRegion().trim()));
        region.setNom(dto.getNomRegion());
        return region;
    }
}
