package fr.diginamic.qualiair.dto.carte;

import java.time.LocalDateTime;
import java.util.Map;

public class FiveDaysForecastView {
    private Map<LocalDateTime, DetailMeteo> forecasts;

    public FiveDaysForecastView() {
    }

    public void addForecast(LocalDateTime targetTime, DetailMeteo forecast) {
        this.forecasts.put(targetTime, forecast);

    }

    /**
     * Getter
     *
     * @return forecast
     */
    public Map<LocalDateTime, DetailMeteo> getForecasts() {
        return forecasts;
    }

    /**
     * Setter
     *
     * @param forecasts sets value
     */
    public void setForecasts(Map<LocalDateTime, DetailMeteo> forecasts) {
        this.forecasts = forecasts;
    }
}
