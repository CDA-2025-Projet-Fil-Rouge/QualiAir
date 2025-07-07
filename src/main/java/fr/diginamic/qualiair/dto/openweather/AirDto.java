package fr.diginamic.qualiair.dto.openweather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AirDto {
    private String dt;
    private MainData main;
    private AirMeasureDto components;

    public AirDto() {
    }

    /**
     * Getter
     *
     * @return dt
     */
    public String getDt() {
        return dt;
    }

    /**
     * Setter
     *
     * @param dt sets value
     */
    public void setDt(String dt) {
        this.dt = dt;
    }

    /**
     * Getter
     *
     * @return main
     */
    public MainData getMain() {
        return main;
    }

    /**
     * Setter
     *
     * @param main sets value
     */
    public void setMain(MainData main) {
        this.main = main;
    }

    /**
     * Getter
     *
     * @return components
     */
    public AirMeasureDto getComponents() {
        return components;
    }

    /**
     * Setter
     *
     * @param components sets value
     */
    public void setComponents(AirMeasureDto components) {
        this.components = components;
    }
}
