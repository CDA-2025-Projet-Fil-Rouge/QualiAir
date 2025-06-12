package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.entity.Departement;
import org.springframework.stereotype.Component;

@Component
public class DepartementMapper {
    public Departement toEntityFromCommuneCoordDto(CommuneCoordDto dto) {
        Departement departement = new Departement();

        departement.setNom(dto.getNomDepartement());
        departement.setCode(dto.getCodeDepartement());

        return departement;
    }
}
