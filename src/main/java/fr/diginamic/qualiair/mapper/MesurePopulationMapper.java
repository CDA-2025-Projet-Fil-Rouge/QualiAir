package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.exception.ParsedDataException;

import java.time.LocalDateTime;
import java.util.List;

public interface MesurePopulationMapper {
    /**
     * Map a dto from csv to entity
     *
     * @param dto dto from csv
     * @return entity
     */
    MesurePopulation toEntity(CommuneHabitantDto dto, LocalDateTime date) throws ParsedDataException;

    HistoriquePopulation toHistoricalDto(List<MesurePopulation> mesures);
}
