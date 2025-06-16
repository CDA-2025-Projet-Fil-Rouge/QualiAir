package fr.diginamic.qualiair.dto.openweather.current;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    private String main;
    private String description;
}
