package fr.diginamic.qualiair.dto.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastFiveDayDto extends OpenWeatherForecastDto {
    List<CurrentForecastDto> currentForecastDtos;

    public ForecastFiveDayDto() {
    }

    /**
     * Getter
     *
     * @return currentForecastDtos
     */
    public List<CurrentForecastDto> getCurrentForecastDtos() {
        return currentForecastDtos;
    }

    /**
     * Setter
     *
     * @param currentForecastDtos sets value
     */
    public void setCurrentForecastDtos(List<CurrentForecastDto> currentForecastDtos) {
        this.currentForecastDtos = currentForecastDtos;
    }
}
