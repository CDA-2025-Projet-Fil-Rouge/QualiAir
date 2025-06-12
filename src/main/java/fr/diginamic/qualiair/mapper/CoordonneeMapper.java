package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.entity.Coordonnee;
import org.springframework.stereotype.Component;

import static fr.diginamic.qualiair.utils.CoordonneeUtils.toDouble;

@Component
public class CoordonneeMapper {
    public Coordonnee toEntityFromCommuneCoordDto(CommuneCoordDto dto) {
        Coordonnee coordonnee = new Coordonnee();

        coordonnee.setLatitude(toDouble(dto.getLatitude()));
        coordonnee.setLongitude(toDouble(dto.getLongitude()));

        return coordonnee;
    }
}
