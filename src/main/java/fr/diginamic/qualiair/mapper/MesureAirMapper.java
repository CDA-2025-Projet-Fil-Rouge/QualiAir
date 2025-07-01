package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.atmofrance.AirDataFeatureDto;
import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.notification.AlerteInfo;
import fr.diginamic.qualiair.dto.openweather.LocalAirQualityDto;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.enumeration.AirPolluant;

import java.time.LocalDateTime;
import java.util.List;

public interface MesureAirMapper {
    /**
     * @param feature objet réponse de l'api Atmo-France
     * @return Liste de mesures Air
     */
    List<MesureAir> toEntityList(AirDataFeatureDto feature, LocalDateTime timeStamp);

    HistoriqueAirQuality toDto(AirPolluant polluant, List<MesureAir> mesures);

    AlerteInfo toDto(MesureAir mesure);

    List<MesureAir> toEntityList(LocalAirQualityDto dto, Coordonnee coordonnee, LocalDateTime timestamp);
}
