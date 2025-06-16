package fr.diginamic.qualiair.dto.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO réponse Open Weather
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    private String main;
    private String description;

    /**
     * Getter
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter
     *
     * @param description sets value
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter
     *
     * @return main
     */
    public String getMain() {
        return main;
    }

    /**
     * Setter
     *
     * @param main sets value
     */
    public void setMain(String main) {
        this.main = main;
    }
}
