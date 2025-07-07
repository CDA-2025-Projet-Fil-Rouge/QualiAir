package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.RegionDto;
import fr.diginamic.qualiair.entity.Region;
import org.springframework.stereotype.Component;

/**
 * Region mapper
 */
@Component
public class RegionMapperImpl implements RegionMapper {


    @Override
    public Region toEntity(RegionDto dto) {
        Region region = new Region();
        region.setCode(Integer.parseInt(dto.getCodeRegion().trim()));
        region.setNom(dto.getNomRegion());
        return region;
    }
}
