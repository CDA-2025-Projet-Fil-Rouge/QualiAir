package fr.diginamic.qualiair.factory;

import fr.diginamic.qualiair.dto.openweather.OpenWeatherForecastDto;
import fr.diginamic.qualiair.entity.Coordonnee;
import fr.diginamic.qualiair.entity.MesurePrevision;

import java.time.LocalDateTime;
import java.util.List;

public interface MesurePrevisionFactory {
    /**
     * Map le dto réponse en une liste de Mesure prévision datées à l'enregistremenet et à la date de relevé
     *
     * @param dto dto réponse OpenWeather
     * @return Liste de mesures prevision
     */
    List<MesurePrevision> getInstanceList(OpenWeatherForecastDto dto, LocalDateTime timeStamp, Coordonnee coordonnee);
}
