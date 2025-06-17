package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.InfoCarteCommune;
import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.dto.insertion.CommuneHabitantDto;
import fr.diginamic.qualiair.entity.Commune;
import org.springframework.stereotype.Component;

/**
 * Mapper for Commune
 */
@Component
public class CommuneMapper {

    /**
     * Map a dto from csv to entity
     *
     * @param dto dto from csv
     * @return entity
     */
    public Commune toEntityFromCommuneCoordDto(CommuneCoordDto dto) {
        Commune commune = new Commune();
        commune.setNomSimple(dto.getNomCommuneSimple());
        commune.setNomReel(dto.getNomCommuneReel());
        commune.setCodePostal(dto.getCodePostal());
        commune.setCodeInsee(dto.getCodeCommuneINSEE());
        return commune;
    }

    public Commune toEntityFromCommuneHabitantDto(CommuneHabitantDto dto) {
        return null; // todo if needed
    }

    public InfoCarteCommune toDto(Commune commune) {
        InfoCarteCommune dto = new InfoCarteCommune();
        return new InfoCarteCommune();
    }
}
