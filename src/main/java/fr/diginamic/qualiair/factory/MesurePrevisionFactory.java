package fr.diginamic.qualiair.factory;

import fr.diginamic.qualiair.dto.openweather.CurrentForecastDto;
import fr.diginamic.qualiair.dto.openweather.ForecastFiveDayDto;
import fr.diginamic.qualiair.dto.openweather.ForecastSixteenDays;
import fr.diginamic.qualiair.dto.openweather.OpenWeatherForecastDto;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.exception.ParsedDataException;
import fr.diginamic.qualiair.mapper.MesurePrevisionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
     * @throws ParsedDataException erreur de conversion de donnée
     */
    public List<MesurePrevision> getInstanceList(OpenWeatherForecastDto dto) throws ParsedDataException {
        if (dto instanceof CurrentForecastDto currentForecastDto) {
            return mapper.toEntityListFromCurrentWeather(currentForecastDto);
        }
        if (dto instanceof ForecastFiveDayDto fiveDayDto) {
            return mapper.toEntityListFromFiveDaysForecast(fiveDayDto);
        }
        if (dto instanceof ForecastSixteenDays sixteenDays) {
            return mapper.toEntityListFromSixteenDaysForecast(sixteenDays);
        } else {
            throw new UnsupportedOperationException("DTO type not supported: " + dto.getClass());
        }
    }
}
