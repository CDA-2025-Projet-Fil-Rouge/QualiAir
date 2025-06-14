package fr.diginamic.qualiair.dto.openweather.current;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RainOneHour {
    @JsonProperty("1h")
    private String oneHour;

}
