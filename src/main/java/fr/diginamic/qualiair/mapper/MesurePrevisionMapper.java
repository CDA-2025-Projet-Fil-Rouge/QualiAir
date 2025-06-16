package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.openweather.*;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.entity.TypeReleve;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fr.diginamic.qualiair.utils.MesureUtils.addIfNotNull;

@Component
public class MesurePrevisionMapper {

    public List<MesurePrevision> toEntityList(CurrentForecastDto dto, TypeReleve typeReleve) throws ParsedDataException {
        List<MesurePrevision> mesures = new ArrayList<>();

        LocalDateTime dateReleve = DateUtils.toLocalDateTime(dto.getDt());
        LocalDateTime dateMaj = LocalDateTime.now();

        Temperature temp = dto.getMain();
        if (temp != null) {
            addIfNotNull(mesures, temp.getTemp(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.TEMPERATURE);
            addIfNotNull(mesures, temp.getFeels_like(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.TEMPERATURE_FELT);
            addIfNotNull(mesures, temp.getTemp_max(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.TEMPERATURE_MAX);
            addIfNotNull(mesures, temp.getTemp_min(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.TEMPERATURE_MIN);
            addIfNotNull(mesures, temp.getHumidity(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.HUMIDITY);
            addIfNotNull(mesures, temp.getPressure(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.PRESSURE);
        }

        addIfNotNull(mesures, dto.getVisibility(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.VISIBILITY);

        Wind wind = dto.getWind();
        if (wind != null) {
            addIfNotNull(mesures, wind.getSpeed(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.WIND_SPEED);
            addIfNotNull(mesures, wind.getGust(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.WIND_SPEED_GUST);
            addIfNotNull(mesures, wind.getDeg(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.WIND_ORIENTATION);
        }

        Clouds clouds = dto.getClouds();
        if (clouds != null) {
            addIfNotNull(mesures, clouds.getAll(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.CLOUD_COVERAGE);
        }

        Rain rain = dto.getRain();
        if (rain != null) {
            addIfNotNull(mesures, rain.getRainOneHour(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.RAIN_1H);
            addIfNotNull(mesures, rain.getRainThreeHour(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.RAIN_3H);
        }

        Snow snow = dto.getSnow();
        if (snow != null) {
            addIfNotNull(mesures, snow.getSnowOneH(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.SNOW_1H);
            addIfNotNull(mesures, snow.getSnowThreeH(), typeReleve, dateReleve, dateMaj, NatureMesurePrevision.SNOW_3H);
        }

        return mesures;
    }

    public List<MesurePrevision> toEntityListFromCurrentWeather(CurrentForecastDto dto) throws ParsedDataException {
        return toEntityList(dto, TypeReleve.ACTUEL);
    }

    public List<MesurePrevision> toEntityListFromFiveDaysForecast(ForecastFiveDayDto dto) throws ParsedDataException {
        List<MesurePrevision> mesures = new ArrayList<>();
        for (CurrentForecastDto dailyForecast : dto.getCurrentForecastDtos()) {
            mesures.addAll(toEntityList(dailyForecast, TypeReleve.PREVISION_5J));
        }
        return mesures;
    }

    public List<MesurePrevision> toEntityListFromSixteenDaysForecast(ForecastSixteenDays dto) {
        return List.of(); // todo impl
    }


}
