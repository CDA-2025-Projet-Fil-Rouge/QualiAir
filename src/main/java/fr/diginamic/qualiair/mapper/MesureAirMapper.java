package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.atmofrance.AirDataFeatureDto;
import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.notification.AlerteInfo;
import fr.diginamic.qualiair.dto.openweather.LocalAirQualityDto;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.enumeration.GeographicalScope;

import java.time.LocalDateTime;
import java.util.List;

public interface MesureAirMapper {
    /**
     * @param feature objet réponse de l'api Atmo-France
     * @return Liste de mesures Air
     */
    List<MesureAir> toEntityListFromAtmoFranceApi(AirDataFeatureDto feature, LocalDateTime timeStamp, Coordonnee coordonnee);

    HistoriqueAirQuality toHistoriqueDto(GeographicalScope scope, String code, AirPolluant polluant, List<MesureAir> mesures);

    AlerteInfo toAlerteDto(MesureAir mesure);

    List<MesureAir> toEntityListFromOpenWeatherApi(LocalAirQualityDto dto, Coordonnee coordonnee, LocalDateTime timestamp);

    HistoriqueAirQuality toHistoriqueDtoFromDepartement(GeographicalScope scope, String codeDept, AirPolluant polluant, List<MesureAir> mAirs);

    HistoriqueAirQuality toHistoriqueDtoFromRegion(GeographicalScope scope, String codeRegion, AirPolluant polluant, List<MesureAir> mAirs);
}
