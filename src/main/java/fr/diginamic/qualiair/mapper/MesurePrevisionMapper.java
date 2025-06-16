package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.openweather.CurrentForecastDto;
import fr.diginamic.qualiair.dto.openweather.OpenWeatherForecastDto;
import fr.diginamic.qualiair.entity.MesurePrevision;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MesurePrevisionMapper {


    public List<MesurePrevision> toEntityListFromCurrentForecast(CurrentForecastDto dto) {
        return null;//todo impl
    }


    public List<MesurePrevision> toEntityListFromFiveDaysForecast(OpenWeatherForecastDto dto) {
        return null; //todo impl
    }

    public List<MesurePrevision> toEntityListFromSixteenDaysForecast(OpenWeatherForecastDto dto) {
        return null; // todo impl
    }


}
