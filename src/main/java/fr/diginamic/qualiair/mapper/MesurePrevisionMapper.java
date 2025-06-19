package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.dto.openweather.*;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
import fr.diginamic.qualiair.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fr.diginamic.qualiair.utils.MesureUtils.addIfNotNull;

@Component
public class MesurePrevisionMapper {

    /**
     * Cette méthode est utilisée par plusieurs Objets de réponse meteo issus de l'api Open Weather. Ajoute une mesure meteo pour chaque fields non vide;
     *
     * @param dto        réponse api
     * @param typeReleve type de relevé généré par la methode
     * @return liste de relevé
     */
    public List<MesurePrevision> toEntityList(CurrentForecastDto dto, TypeReleve typeReleve, LocalDateTime timeStamp) {
        List<MesurePrevision> mesures = new ArrayList<>();

        LocalDateTime dateReleve = DateUtils.toLocalDateTime(dto.getDt());

        Temperature temp = dto.getMain();
        if (temp != null) {
            addIfNotNull(mesures, temp.getTemp(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.TEMPERATURE);
            addIfNotNull(mesures, temp.getFeels_like(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.TEMPERATURE_FELT);
            addIfNotNull(mesures, temp.getTemp_max(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.TEMPERATURE_MAX);
            addIfNotNull(mesures, temp.getTemp_min(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.TEMPERATURE_MIN);
            addIfNotNull(mesures, temp.getHumidity(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.HUMIDITY);
            addIfNotNull(mesures, temp.getPressure(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.PRESSURE);
        }

        addIfNotNull(mesures, dto.getVisibility(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.VISIBILITY);

        Wind wind = dto.getWind();
        if (wind != null) {
            addIfNotNull(mesures, wind.getSpeed(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.WIND_SPEED);
            addIfNotNull(mesures, wind.getGust(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.WIND_SPEED_GUST);
            addIfNotNull(mesures, wind.getDeg(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.WIND_ORIENTATION);
        }

        Clouds clouds = dto.getClouds();
        if (clouds != null) {
            addIfNotNull(mesures, clouds.getAll(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.CLOUD_COVERAGE);
        }

        Rain rain = dto.getRain();
        if (rain != null) {
            addIfNotNull(mesures, rain.getRainOneHour(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.RAIN_1H);
            addIfNotNull(mesures, rain.getRainThreeHour(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.RAIN_3H);
        }

        Snow snow = dto.getSnow();
        if (snow != null) {
            addIfNotNull(mesures, snow.getSnowOneH(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.SNOW_1H);
            addIfNotNull(mesures, snow.getSnowThreeH(), typeReleve, dateReleve, timeStamp, NatureMesurePrevision.SNOW_3H);
        }

        return mesures;
    }

    /**
     * Conversion pour les reuqetes de meteo instantannées
     *
     * @param dto objet reponse api
     * @return list de mesures
     */
    public List<MesurePrevision> toEntityListFromCurrentWeather(CurrentForecastDto dto, LocalDateTime timeStamp) {
        return toEntityList(dto, TypeReleve.ACTUEL, timeStamp);
    }

    /**
     * Conversion pour les reuqetes de prévision meteo 5j
     *
     * @param dto objet reponse api
     * @return list de mesures
     */
    public List<MesurePrevision> toEntityListFromFiveDaysForecast(ForecastFiveDayDto dto, LocalDateTime timeStamp) {
        List<MesurePrevision> mesures = new ArrayList<>();
        for (CurrentForecastDto dailyForecast : dto.getCurrentForecastDtos()) {
            mesures.addAll(toEntityList(dailyForecast, TypeReleve.PREVISION_5J, timeStamp));
        }
        return mesures;
    }

    /**
     * Conversion pour les reuqetes de prévision meteo 16j
     *
     * @param dto objet reponse api
     * @return list de mesures
     * //     * @throws ParsedDataException erreurs de parsing
     */
    public List<MesurePrevision> toEntityListFromSixteenDaysForecast(ForecastSixteenDays dto, LocalDateTime timeStamp) {
        return List.of(); // todo impl
    }


    public HistoriquePrevision toHistoricalDto(NatureMesurePrevision nature, List<MesurePrevision> mesures) {
        HistoriquePrevision dto = new HistoriquePrevision();
        dto.setNature(nature.toString());
        dto.setUnite(mesures.getFirst().getUnite());
        for (MesurePrevision m : mesures) {
            dto.addValeur(m.getDateEnregistrement(), m.getValeur());
        }
        return dto;
    }
}
