package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.DepartementDto;
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
    public Departement toEntity(DepartementDto dto) {
        Departement departement = new Departement();
        departement.setCode(dto.getCodeDepartement());
        departement.setNom(dto.getNomDepartement());
        return departement;
    }
}
