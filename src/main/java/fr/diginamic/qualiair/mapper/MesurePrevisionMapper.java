package fr.diginamic.qualiair.mapper;

import fr.diginamic.qualiair.dto.openweather.CurrentForecastDto;
import fr.diginamic.qualiair.dto.openweather.OpenWeatherForecastDto;
import fr.diginamic.qualiair.dto.openweather.Temperature;
import fr.diginamic.qualiair.entity.MesurePrevision;
import fr.diginamic.qualiair.entity.MesurePrevisionProbabilite;
import fr.diginamic.qualiair.entity.NatureMesurePrevision;
import fr.diginamic.qualiair.entity.TypeMesure;
import fr.diginamic.qualiair.utils.DateUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MesurePrevisionMapper {


    public List<MesurePrevision> toEntityListFromCurrentForecast(CurrentForecastDto dto) {
        List<MesurePrevision> mesures = new ArrayList<>();

        LocalDateTime dateReleve = DateUtils.toLocalDateTime(dto.getDt());
        LocalDateTime dateMaj = LocalDateTime.now();

        Temperature temp = dto.getMain();
        if(temp.getTemp() != null){

        }

        return null;//todo impl
    }


    public List<MesurePrevision> toEntityListFromFiveDaysForecast(OpenWeatherForecastDto dto) {
        return null; //todo impl
    }

    public List<MesurePrevision> toEntityListFromSixteenDaysForecast(OpenWeatherForecastDto dto) {
        return null; // todo impl
    }


    private MesurePrevision createMesurePrevision() {

        MesurePrevision mesure = new MesurePrevision();
        mesure.setTypeMesure(TypeMesure.RELEVE_METEO);
        mesure.setTypeReleve(typeReleve);
        mesure.setDateReleve(dateReleve);
        mesure.setDateEnregistrement(dateMaj);

        setNatureAndUnite(mesure, n)

        return mesure;
    }

    private MesurePrevision setNatureAndUnite(MesurePrevision mesure, NatureMesurePrevision nature) {
        mesure.setNature(nature);
        switch (nature) {
            case HUMIDITY, CLOUD_COVERAGE -> mesure.setUnite("%");
            case TEMPERATURE, TEMPERATURE_MAX, TEMPERATURE_FELT, TEMPERATURE_MIN -> mesure.setUnite("Celcius");
            case PRESSURE -> mesure.setUnite("hpa");
            case VISIBILITY -> mesure.setUnite("km");
            case WIND_SPEED, WIND_SPEED_GUST -> mesure.setUnite("m/s");
            case WIND_ORIENTATION -> mesure.setUnite("degre");
            default -> mesure.setUnite("tbd");
        }
    }
}
