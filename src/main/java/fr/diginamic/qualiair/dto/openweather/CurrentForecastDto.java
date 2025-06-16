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

    public CurrentForecastDto() {
    }

    /**
     * Getter
     *
     * @return coord
     */
    public Coordinates getCoord() {
        return coord;
    }

    /**
     * Setter
     *
     * @param coord sets value
     */
    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }

    /**
     * Getter
     *
     * @return weather
     */
    public List<Weather> getWeather() {
        return weather;
    }

    /**
     * Setter
     *
     * @param weather sets value
     */
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    /**
     * Getter
     *
     * @return base
     */
    public String getBase() {
        return base;
    }

    /**
     * Setter
     *
     * @param base sets value
     */
    public void setBase(String base) {
        this.base = base;
    }

    /**
     * Getter
     *
     * @return main
     */
    public Temperature getMain() {
        return main;
    }

    /**
     * Setter
     *
     * @param main sets value
     */
    public void setMain(Temperature main) {
        this.main = main;
    }

    /**
     * Getter
     *
     * @return visibility
     */
    public int getVisibility() {
        return visibility;
    }

    /**
     * Setter
     *
     * @param visibility sets value
     */
    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    /**
     * Getter
     *
     * @return wind
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * Setter
     *
     * @param wind sets value
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * Getter
     *
     * @return rain
     */
    public RainOneHour getRain() {
        return rain;
    }

    /**
     * Setter
     *
     * @param rain sets value
     */
    public void setRain(RainOneHour rain) {
        this.rain = rain;
    }

    /**
     * Getter
     *
     * @return clouds
     */
    public Clouds getClouds() {
        return clouds;
    }

    /**
     * Setter
     *
     * @param clouds sets value
     */
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    /**
     * Getter
     *
     * @return dt
     */
    public long getDt() {
        return dt;
    }

    /**
     * Setter
     *
     * @param dt sets value
     */
    public void setDt(long dt) {
        this.dt = dt;
    }

    /**
     * Getter
     *
     * @return timezone
     */
    public int getTimezone() {
        return timezone;
    }

    /**
     * Setter
     *
     * @param timezone sets value
     */
    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    /**
     * Getter
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter
     *
     * @param name sets value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter
     *
     * @return cod
     */
    public int getCod() {
        return cod;
    }

    /**
     * Setter
     *
     * @param cod sets value
     */
    public void setCod(int cod) {
        this.cod = cod;
    }
}
