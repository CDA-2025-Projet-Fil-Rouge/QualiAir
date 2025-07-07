package fr.diginamic.qualiair.dto.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * DTO réponse Open Weather
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalAirQualityDto {
    private Coordinates coord;
    private List<AirDto> list;

    public LocalAirQualityDto() {
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
     * @return list
     */
    public List<AirDto> getList() {
        return list;
    }

    /**
     * Setter
     *
     * @param list sets value
     */
    public void setList(List<AirDto> list) {
        this.list = list;
    }
}
