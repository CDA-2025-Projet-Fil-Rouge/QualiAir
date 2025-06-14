package fr.diginamic.qualiair.dto.atmofrance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyAirDataDto {
    @JsonProperty("features")
    private List<AirDataFeatureDto> features;

    public DailyAirDataDto() {
    }

    /**
     * Getter
     *
     * @return features
     */
    public List<AirDataFeatureDto> getFeatures() {
        return features;
    }

    /**
     * Setter
     *
     * @param features sets value
     */
    public void setFeatures(List<AirDataFeatureDto> features) {
        this.features = features;
    }
}
