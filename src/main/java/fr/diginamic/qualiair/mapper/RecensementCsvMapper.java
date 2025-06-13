package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import org.springframework.stereotype.Component;

@Component
public class RecensementCsvMapper {

    public CommuneCoordDto mapToCommuneCoordDto(String[] tokens) {
//        System.out.println(tokens.length);
        CommuneCoordDto dto = new CommuneCoordDto();
        dto.setCodeCommuneINSEE(tokens[0]);
        dto.setNomCommunePostal(tokens[1]);
        dto.setCodePostal(tokens[2]);
        dto.setLatitude(tokens[5]);
        dto.setLongitude(tokens[6]);
        dto.setNomCommuneComplet(tokens[10].trim().replace(" ", ""));
        dto.setCodeDepartement(tokens[11]);
        dto.setNomDepartement(tokens[12]);
        dto.setCodeRegion(tokens[13]);
        dto.setNomRegion(tokens[14]);
        return dto;
    }

    public CommuneHabitantDto mapToCommuneHabitantDto(String[] tokens) {
//        System.out.println(tokens.length);
        CommuneHabitantDto dto = new CommuneHabitantDto();
        dto.setNomCommune(tokens[6].trim().replace(" ", ""));
        dto.setPopulationTotale(tokens[9].trim().replace(" ", ""));
        dto.setPopulationMunicipale(tokens[7].trim().replace(" ", ""));
        return dto;
    }
}
