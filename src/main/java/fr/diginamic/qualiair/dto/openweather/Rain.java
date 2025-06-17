package fr.diginamic.qualiair.dto.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO réponse Open Weather
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rain {
    @JsonProperty("1h")
    private String rainOneHour;
    @JsonProperty("3h")
    private String rainThreeHour;

    public Rain() {
    }

    /**
     * Getter
     *
     * @return rainOneHour
     */
    public String getRainOneHour() {
        return rainOneHour;
    }

    /**
     * Setter
     *
     * @param rainOneHour sets value
     */
    public void setRainOneHour(String rainOneHour) {
        this.rainOneHour = rainOneHour;
    }

    /**
     * Getter
     *
     * @return rainThreeHour
     */
    public String getRainThreeHour() {
        return rainThreeHour;
    }

    /**
     * Setter
     *
     * @param rainThreeHour sets value
     */
    public void setRainThreeHour(String rainThreeHour) {
        this.rainThreeHour = rainThreeHour;
    }
}
