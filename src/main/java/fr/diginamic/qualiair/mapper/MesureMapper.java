package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.exception.ParsedDataException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static fr.diginamic.qualiair.utils.MesureUtils.toInt;

/**
 * Mesure Mapper
 */
@Component
public class MesureMapper {
    /**
     * Map a dto from csv to entity
     *
     * @param dto dto from csv
     * @return entity
     */
    public MesurePopulation toEntityFromCommuneCoordDto(CommuneHabitantDto dto) throws ParsedDataException {
        MesurePopulation mesure = new MesurePopulation();
        mesure.setNom("Population");
        mesure.setDate(LocalDateTime.now());
        mesure.setDateEnregistrement(LocalDateTime.now());
        mesure.setValeur(toInt(dto.getPopulationMunicipale().trim().replace(" ", "")));
        return mesure;
    }
}
