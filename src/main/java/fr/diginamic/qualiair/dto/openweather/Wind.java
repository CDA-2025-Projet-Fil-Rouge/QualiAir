package fr.diginamic.qualiair.dto.openweather;

public class Wind {
    private String speed;
    private String deg;
    private String gust;

    public Wind() {
    }

    /**
     * Getter
     *
     * @return speed
     */
    public String getSpeed() {
        return speed;
    }

    /**
     * Setter
     *
     * @param speed sets value
     */
    public void setSpeed(String speed) {
        this.speed = speed;
    }

    /**
     * Getter
     *
     * @return deg
     */
    public String getDeg() {
        return deg;
    }

    /**
     * Setter
     *
     * @param deg sets value
     */
    public void setDeg(String deg) {
        this.deg = deg;
    }

    /**
     * Getter
     *
     * @return gust
     */
    public String getGust() {
        return gust;
    }

    /**
     * Setter
     *
     * @param gust sets value
     */
    public void setGust(String gust) {
        this.gust = gust;
    }
}
