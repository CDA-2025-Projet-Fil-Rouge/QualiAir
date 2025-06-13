package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.entity.Departement;
import org.springframework.stereotype.Component;

/**
 * Departement Mapper
 */
@Component
public class DepartementMapper {
    /**
     * Map a dto from csv to entity
     *
     * @param dto dto from csv
     * @return entity
     */
    public Departement toEntityFromCommuneCoordDto(CommuneCoordDto dto) {
        Departement departement = new Departement();

        departement.setNom(dto.getNomDepartement());
        departement.setCode(dto.getCodeDepartement());

        return departement;
    }
}
