package fr.diginamic.qualiair.dto.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RainThreeHours {
    @JsonProperty("3h")
    private String threeHour;
}
