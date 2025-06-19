package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.DepartementDto;
import fr.diginamic.qualiair.entity.Departement;

public interface DepartementMapper {
    /**
     * Map a dto from csv to entity
     *
     * @param dto dto from csv
     * @return entity
     */
    Departement toEntity(DepartementDto dto);
}
