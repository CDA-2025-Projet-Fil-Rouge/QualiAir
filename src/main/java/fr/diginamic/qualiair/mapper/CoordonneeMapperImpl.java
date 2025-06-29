package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.atmofrance.AirDataFeatureDto;
import fr.diginamic.qualiair.dto.insertion.CommuneCoordDto;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.exception.ParsedDataException;
import org.springframework.stereotype.Component;

import static fr.diginamic.qualiair.utils.CoordonneeUtils.toDouble;

/**
 * Coordonnee mapper
 */
@Component
public class CoordonneeMapperImpl implements CoordonneeMapper {
    @Override
    public Coordonnee toEntityFromCommuneCoordDto(CommuneCoordDto dto) throws ParsedDataException {
        Coordonnee coordonnee = new Coordonnee();

        coordonnee.setLatitude(toDouble(dto.getLatitude()));
        coordonnee.setLongitude(toDouble(dto.getLongitude()));

        return coordonnee;
    }


    @Override
    public Coordonnee toEntityFromAirDataTo(AirDataFeatureDto feature) throws ParsedDataException {
        Coordonnee coordonnee = new Coordonnee();
        coordonnee.setLatitude(toDouble(feature.getProperties().getyWgs84()));
        coordonnee.setLongitude(toDouble(feature.getProperties().getxWgs84()));
        return coordonnee;
    }
}
