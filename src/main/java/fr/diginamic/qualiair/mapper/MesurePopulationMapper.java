package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.enumeration.GeographicalScope;
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
    MesurePopulation toEntityFromCsv(CommuneHabitantDto dto, LocalDateTime date, LocalDateTime timeStamp, Coordonnee coordonnee) throws ParsedDataException;

    HistoriquePopulation toHistoricalDto(GeographicalScope scope, String code, List<MesurePopulation> mesures);
}
