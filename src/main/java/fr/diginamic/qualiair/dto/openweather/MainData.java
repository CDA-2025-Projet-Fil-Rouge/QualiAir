package fr.diginamic.qualiair.dto.openweather;

public class MainData {
    private String aqi;

    public MainData() {
    }

    /**
     * Getter
     *
     * @return aqi
     */
    public String getAqi() {
        return aqi;
    }

    /**
     * Setter
     *
     * @param aqi sets value
     */
    public void setAqi(String aqi) {
        this.aqi = aqi;
    }
}
