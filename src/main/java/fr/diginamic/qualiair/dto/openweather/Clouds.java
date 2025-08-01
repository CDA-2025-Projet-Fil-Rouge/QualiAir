package fr.diginamic.qualiair.dto.openweather;

/**
 * DTO réponse Open Weather
 */
public class Clouds {
    private String all;

    public Clouds() {
    }

    /**
     * Getter
     *
     * @return all
     */
    public String getAll() {
        return all;
    }

    /**
     * Setter
     *
     * @param all sets value
     */
    public void setAll(String all) {
        this.all = all;
    }
}
