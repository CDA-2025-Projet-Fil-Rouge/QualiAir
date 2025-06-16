package fr.diginamic.qualiair.dto.atmofrance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AirDataFeatureDto {
    @JsonProperty("properties")
    private AirDataPropertiesDto properties;

    public AirDataFeatureDto() {
    }

    /**
     * Getter
     *
     * @return properties
     */
    public AirDataPropertiesDto getProperties() {
        return properties;
    }

    /**
     * Setter
     *
     * @param properties sets value
     */
    public void setProperties(AirDataPropertiesDto properties) {
        this.properties = properties;
    }
}
