package fr.diginamic.qualiair.factory;

import fr.diginamic.qualiair.dto.openweather.CurrentForecastDto;
import fr.diginamic.qualiair.dto.openweather.ForecastFiveDayDto;
import fr.diginamic.qualiair.dto.openweather.ForecastSixteenDays;
import fr.diginamic.qualiair.dto.openweather.OpenWeatherForecastDto;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.mapper.MesurePrevisionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Factory pour les Mesures Prévision
 */
@Component
public class MesurePrevisionFactory {
    @Autowired
    private MesurePrevisionMapper mapper;

    /**
     * Map le dto réponse en une liste de Mesure prévision datées à l'enregistremenet et à la date de relevé
     *
     * @param dto dto réponse OpenWeather
     * @return Liste de mesures prevision
     */
    public List<MesurePrevision> getInstanceList(OpenWeatherForecastDto dto, LocalDateTime timeStamp) {
        if (dto instanceof CurrentForecastDto currentForecastDto) {
            return mapper.toEntityListFromCurrentWeather(currentForecastDto, timeStamp);
        }
        if (dto instanceof ForecastFiveDayDto fiveDayDto) {
            return mapper.toEntityListFromFiveDaysForecast(fiveDayDto, timeStamp);
        }
        if (dto instanceof ForecastSixteenDays sixteenDays) {
            return mapper.toEntityListFromSixteenDaysForecast(sixteenDays, timeStamp);
        } else {
            throw new UnsupportedOperationException("DTO type not supported: " + dto.getClass());
        }
    }
}
