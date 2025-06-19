package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.dto.insertion.DepartementDto;
import fr.diginamic.qualiair.dto.insertion.RegionDto;
import fr.diginamic.qualiair.utils.DepartementUtils;
import org.springframework.stereotype.Component;

/**
 * RecensementCsv files mapper
 */
@Component
public class RecensementCsvMapperImpl implements RecensementCsvMapper {

    @Override
    public CommuneCoordDto mapToCommuneCoordDto(String[] tokens) {
        CommuneCoordDto dto = new CommuneCoordDto();
        dto.setCodeDepartement(tokens[1]);
        dto.setNomCommune(tokens[2]);
        dto.setNomCommuneSimple(tokens[3]);
        dto.setNomCommuneReel(tokens[4]);
        dto.setCodePostal(tokens[5]);
        dto.setCodeCommuneINSEE(tokens[6]);
        dto.setLatitude(tokens[8]);
        dto.setLongitude(tokens[9]);

        return dto;
    }

    @Override
    public CommuneHabitantDto mapToCommuneHabitantDto(String[] tokens) {
        CommuneHabitantDto dto = new CommuneHabitantDto();
        dto.setCodeInsee(tokens[6].trim().replace(" ", ""));
        dto.setPopulationMunicipale(tokens[8].trim().replace(" ", ""));
        return dto;
    }

    @Override
    public DepartementDto mapToDepartementdto(String[] strings) {
        DepartementDto dto = new DepartementDto();
        dto.setCodeDepartement(DepartementUtils.normalizeCodeDetp(strings[1]));
        dto.setNomDepartement(strings[2]);
        dto.setRegionId(strings[3]);
        return dto;
    }

    @Override
    public RegionDto mapToRegionDto(String[] strings) {
        RegionDto dto = new RegionDto();
        dto.setCodeRegion(strings[1]);
        dto.setNomRegion(strings[2]);
        dto.setId(strings[0]);
        return dto;
    }
}
