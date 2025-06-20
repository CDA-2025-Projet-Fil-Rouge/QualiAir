package fr.diginamic.qualiair.dto.carte;

import fr.diginamic.qualiair.enumeration.AirPolluant;

import java.util.HashMap;
import java.util.Map;

public class InfoCarteCommuneDetailQualiteAir {
    private Map<AirPolluant, Integer> indices = new HashMap<>();

    public InfoCarteCommuneDetailQualiteAir() {
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

    public Integer getIndice(AirPolluant polluant) {
        return this.indices.get(polluant);
    }

}
