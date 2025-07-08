package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.dto.openweather.CurrentForecastDto;
import fr.diginamic.qualiair.dto.openweather.ForecastFiveDayDto;
import fr.diginamic.qualiair.dto.openweather.ForecastSixteenDays;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
import fr.diginamic.qualiair.enumeration.GeographicalScope;

import java.time.LocalDateTime;
import java.util.List;

public interface MesurePrevisionMapper {
    /**
     * Cette méthode est utilisée par plusieurs Objets de réponse meteo issus de l'api Open Weather. Ajoute une mesure meteo pour chaque fields non vide;
     *
     * @param dto        réponse api
     * @param typeReleve type de relevé généré par la methode
     * @return liste de relevé
     */
    List<MesurePrevision> toEntityList(CurrentForecastDto dto, TypeReleve typeReleve, LocalDateTime timeStamp, Coordonnee coordonnee);

    /**
     * Conversion pour les reuqetes de meteo instantannées
     *
     * @param dto objet reponse api
     * @return list de mesures
     */
    List<MesurePrevision> toEntityListFromCurrentWeather(CurrentForecastDto dto, LocalDateTime timeStamp, Coordonnee coordonnee);

    /**
     * Conversion pour les reuqetes de prévision meteo 5j
     *
     * @param dto objet reponse api
     * @return list de mesures
     */
    List<MesurePrevision> toEntityListFromFiveDaysForecast(ForecastFiveDayDto dto, LocalDateTime timeStamp, Coordonnee coordonnee);

    /**
     * Conversion pour les reuqetes de prévision meteo 16j
     *
     * @param dto objet reponse api
     * @return list de mesures
     * //     * @throws ParsedDataException erreurs de parsing
     */
    List<MesurePrevision> toEntityListFromSixteenDaysForecast(ForecastSixteenDays dto, LocalDateTime timeStamp, Coordonnee coordonnee);

    HistoriquePrevision toHistoricalDto(GeographicalScope scope, String scopedCode, NatureMesurePrevision nature, List<MesurePrevision> mesures);

    HistoriquePrevision toHistoricalDtoFromRegion(GeographicalScope scope, String code, NatureMesurePrevision nature, List<MesurePrevision> mesures);

    HistoriquePrevision toHistoricalDtoFromDepartement(GeographicalScope scope, String code, NatureMesurePrevision nature, List<MesurePrevision> mesures);
}
