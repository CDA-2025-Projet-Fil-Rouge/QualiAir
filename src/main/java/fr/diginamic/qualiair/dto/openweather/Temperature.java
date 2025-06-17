package fr.diginamic.qualiair.dto.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO réponse Open Weather
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Temperature {
    private String temp;
    private String feels_like;
    private String temp_min;
    private String temp_max;
    private String pressure;
    private String humidity;

    public Temperature() {
    }

    /**
     * Getter
     *
     * @return temp
     */
    public String getTemp() {
        return temp;
    }

    /**
     * Setter
     *
     * @param temp sets value
     */
    public void setTemp(String temp) {
        this.temp = temp;
    }

    /**
     * Getter
     *
     * @return feels_like
     */
    public String getFeels_like() {
        return feels_like;
    }

    /**
     * Setter
     *
     * @param feels_like sets value
     */
    public void setFeels_like(String feels_like) {
        this.feels_like = feels_like;
    }

    /**
     * Getter
     *
     * @return temp_min
     */
    public String getTemp_min() {
        return temp_min;
    }

    /**
     * Setter
     *
     * @param temp_min sets value
     */
    public void setTemp_min(String temp_min) {
        this.temp_min = temp_min;
    }

    /**
     * Getter
     *
     * @return temp_max
     */
    public String getTemp_max() {
        return temp_max;
    }

    /**
     * Setter
     *
     * @param temp_max sets value
     */
    public void setTemp_max(String temp_max) {
        this.temp_max = temp_max;
    }

    /**
     * Getter
     *
     * @return pressure
     */
    public String getPressure() {
        return pressure;
    }

    /**
     * Setter
     *
     * @param pressure sets value
     */
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    /**
     * Getter
     *
     * @return humidity
     */
    public String getHumidity() {
        return humidity;
    }

    /**
     * Setter
     *
     * @param humidity sets value
     */
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
