package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.entity.Region;
import fr.diginamic.qualiair.exception.ParsedDataException;
import org.springframework.stereotype.Component;

import static fr.diginamic.qualiair.utils.RegionUtils.toInt;

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
    public Region toEntityFromCommuneCoordDto(CommuneCoordDto dto) throws ParsedDataException {
        Region region = new Region();

        region.setNom(dto.getNomRegion());
        region.setCode(toInt(dto.getCodeRegion()));

        return region;
    }
}
