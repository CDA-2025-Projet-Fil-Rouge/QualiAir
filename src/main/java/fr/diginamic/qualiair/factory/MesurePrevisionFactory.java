package fr.diginamic.qualiair.factory;

import fr.diginamic.qualiair.dto.openweather.CurrentForecastDto;
import fr.diginamic.qualiair.dto.openweather.ForecastFiveDayDto;
import fr.diginamic.qualiair.dto.openweather.ForecastSixteenDays;
import fr.diginamic.qualiair.dto.openweather.OpenWeatherForecastDto;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.mapper.MesurePrevisionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MesurePrevisionFactory {
    @Autowired
    private MesurePrevisionMapper mapper;

    public List<MesurePrevision> getInstanceList(OpenWeatherForecastDto dto) {
        if (dto instanceof CurrentForecastDto currentForecastDto) {
            return mapper.toEntityListFromCurrentForecast(currentForecastDto);
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
