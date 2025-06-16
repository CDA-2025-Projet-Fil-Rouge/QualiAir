package fr.diginamic.qualiair.dto.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Snow {
    @JsonProperty("1h")
    private String snowOneH;
    @JsonProperty("3h")
    private String snowThreeH;

    public Snow() {
    }

    /**
     * Getter
     *
     * @return snowOneH
     */
    public String getSnowOneH() {
        return snowOneH;
    }

    /**
     * Setter
     *
     * @param snowOneH sets value
     */
    public void setSnowOneH(String snowOneH) {
        this.snowOneH = snowOneH;
    }

    /**
     * Getter
     *
     * @return snowThreeH
     */
    public String getSnowThreeH() {
        return snowThreeH;
    }

    /**
     * Setter
     *
     * @param snowThreeH sets value
     */
    public void setSnowThreeH(String snowThreeH) {
        this.snowThreeH = snowThreeH;
    }
}
