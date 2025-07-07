package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.dto.insertion.DepartementDto;
import fr.diginamic.qualiair.dto.insertion.RegionDto;

public interface RecensementCsvMapper {
    /**
     * Map a split csv line into a dto
     *
     * @param tokens tokens from csv line
     * @return dto
     */
    CommuneCoordDto mapToCommuneCoordDto(String[] tokens);

    /**
     * Map a split csv line into a dto
     *
     * @param tokens tokens from csv line
     * @return dto
     */
    CommuneHabitantDto mapToCommuneHabitantDto(String[] tokens);

    DepartementDto mapToDepartementdto(String[] strings);

    RegionDto mapToRegionDto(String[] strings);
}
