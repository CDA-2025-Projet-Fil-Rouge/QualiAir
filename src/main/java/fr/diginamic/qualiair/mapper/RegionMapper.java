package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.entity.Region;
import org.springframework.stereotype.Component;

import static fr.diginamic.qualiair.utils.RegionUtils.toInt;

@Component
public class RegionMapper {
    public Region toEntityFromCommuneCoordDto(CommuneCoordDto dto) {
        Region region = new Region();

        region.setNom(dto.getNomRegion());
        region.setCode(toInt(dto.getCodeRegion()));

        return region;
    }
}
