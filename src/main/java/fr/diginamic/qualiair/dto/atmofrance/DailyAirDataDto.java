package fr.diginamic.qualiair.dto.atmofrance;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DailyAirDataDto {
    @JsonProperty("features")
    private List<AirDataFeature> features;

    public DailyAirDataDto() {
    }

    /**
     * Getter
     *
     * @return features
     */
    public List<AirDataFeature> getFeatures() {
        return features;
    }

    /**
     * Setter
     *
     * @param features sets value
     */
    public void setFeatures(List<AirDataFeature> features) {
        this.features = features;
    }
}
