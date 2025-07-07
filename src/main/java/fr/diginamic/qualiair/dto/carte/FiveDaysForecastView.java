package fr.diginamic.qualiair.dto.carte;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FiveDaysForecastView {
    private Long id;
    private String codeInsee;
    private Map<LocalDateTime, DetailMeteo> forecasts = new HashMap<>();

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

    /**
     * Getter
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id sets value
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return codeInsee
     */
    public String getCodeInsee() {
        return codeInsee;
    }

    /**
     * Setter
     *
     * @param codeInsee sets value
     */
    public void setCodeInsee(String codeInsee) {
        this.codeInsee = codeInsee;
    }
}
