package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.entity.MesurePopulation;
import fr.diginamic.qualiair.entity.TypeMesure;
import fr.diginamic.qualiair.exception.ParsedDataException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static fr.diginamic.qualiair.utils.MesureUtils.toInt;

/**
 * Mesure Mapper pour les mesures population
 */
@Component
public class MesurePopulationMapper {

    /**
     * Map a dto from csv to entity
     *
     * @param dto dto from csv
     * @return entity
     */
    public MesurePopulation toEntity(CommuneHabitantDto dto, LocalDateTime date) throws ParsedDataException {
        MesurePopulation mesure = new MesurePopulation();
        mesure.setTypeMesure(TypeMesure.RELEVE_POPULATION);
        mesure.setDateReleve(date);
        mesure.setDateEnregistrement(LocalDateTime.now());
        mesure.setValeur(toInt(dto.getPopulationMunicipale().trim().replace(" ", "")));
        return mesure;
    }

}
