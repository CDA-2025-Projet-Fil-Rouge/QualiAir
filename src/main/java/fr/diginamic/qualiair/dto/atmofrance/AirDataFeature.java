package fr.diginamic.qualiair.dto.atmofrance;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AirDataFeature {
    @JsonProperty("properties")
    private AirDataProperties properties;

    public AirDataFeature() {
    }

    /**
     * Getter
     *
     * @return properties
     */
    public AirDataProperties getProperties() {
        return properties;
    }

    /**
     * Setter
     *
     * @param properties sets value
     */
    public void setProperties(AirDataProperties properties) {
        this.properties = properties;
    }
}
