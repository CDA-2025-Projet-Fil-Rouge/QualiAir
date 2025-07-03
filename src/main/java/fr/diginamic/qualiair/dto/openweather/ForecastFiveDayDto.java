package fr.diginamic.qualiair.dto.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * DTO réponse Open Weather
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastFiveDayDto extends OpenWeatherForecastDto {
    List<CurrentForecastDto> list;
    private String cnt;

    public ForecastFiveDayDto() {
    }

    /**
     * Getter
     *
     * @return currentForecastDtos
     */
    public List<CurrentForecastDto> getList() {
        return list;
    }

    /**
     * Setter
     *
     * @param list sets value
     */
    public void setList(List<CurrentForecastDto> list) {
        this.list = list;
    }

    /**
     * Getter
     *
     * @return cnt
     */
    public String getCnt() {
        return cnt;
    }

    /**
     * Setter
     *
     * @param cnt sets value
     */
    public void setCnt(String cnt) {
        this.cnt = cnt;
    }
}
