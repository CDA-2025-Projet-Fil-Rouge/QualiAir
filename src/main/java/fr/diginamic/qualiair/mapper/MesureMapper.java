package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.entity.MesurePopulation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static fr.diginamic.qualiair.utils.MesureUtils.toInt;

@Component
public class MesureMapper {
    public MesurePopulation toEntityFromCommuneCoordDto(CommuneHabitantDto dto) {
        MesurePopulation mesure = new MesurePopulation();
        mesure.setNom("Population");
        mesure.setDate(LocalDateTime.now());
        mesure.setDateEnregistrement(LocalDateTime.now());
        mesure.setValeur(toInt(dto.getPopulationMunicipale()));
        return mesure;
    }
}
