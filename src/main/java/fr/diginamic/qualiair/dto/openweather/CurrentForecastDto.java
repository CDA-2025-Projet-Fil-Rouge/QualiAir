package fr.diginamic.qualiair.dto.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentForecastDto extends OpenWeatherForecastDto {
    private Coordinates coord;
    private List<Weather> weather;
    private String base;
    private Temperature main;
    private int visibility;
    private Wind wind;
    private RainOneHour rain;
    private Clouds clouds;
    private long dt;
    private int timezone;

    private String name;
    private int cod;
}
