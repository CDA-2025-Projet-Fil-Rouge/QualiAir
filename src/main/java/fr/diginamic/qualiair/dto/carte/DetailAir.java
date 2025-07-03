package fr.diginamic.qualiair.dto.carte;

import fr.diginamic.qualiair.enumeration.AirPolluant;

import java.util.HashMap;
import java.util.Map;

public class DetailAir {
    private Map<AirPolluant, Integer> indices = new HashMap<>();
    private Map<String, Double> values = new HashMap<>();
    private Map<String, String> units = new HashMap<>();

    public DetailAir() {
    }

    public Map<AirPolluant, Integer> getIndices() {
        return indices;
    }

    /**
     * Setter
     *
     * @param indices sets value
     */
    public void setIndices(Map<AirPolluant, Integer> indices) {
        this.indices = indices;
    }

    public void addIndice(AirPolluant polluant, int indice) {
        this.indices.put(polluant, indice);
    }

    public void addValue(String pollutant, Double value) {
        values.put(pollutant, value);
    }

    public void addUnit(String pollutant, String unit) {
        units.put(pollutant, unit);
    }

    public Integer getIndice(AirPolluant polluant) {
        return this.indices.get(polluant);
    }

    /**
     * Getter
     *
     * @return values
     */
    public Map<String, Double> getValues() {
        return values;
    }

    /**
     * Setter
     *
     * @param values sets value
     */
    public void setValues(Map<String, Double> values) {
        this.values = values;
    }

    /**
     * Getter
     *
     * @return units
     */
    public Map<String, String> getUnits() {
        return units;
    }

    /**
     * Setter
     *
     * @param units sets value
     */
    public void setUnits(Map<String, String> units) {
        this.units = units;
    }
}
