package fr.diginamic.qualiair.dto.carte;

import fr.diginamic.qualiair.entity.NatureMesurePrevision;

import java.util.HashMap;
import java.util.Map;

public class DetailMeteo {
    private Map<NatureMesurePrevision, Map<Double, String>> meteo = new HashMap<>();

    public DetailMeteo() {
    }

    /**
     * Getter
     *
     * @return meteo
     */
    public Map<NatureMesurePrevision, Map<Double, String>> getMeteo() {
        return meteo;
    }

    /**
     * Setter
     *
     * @param meteo sets value
     */
    public void setMeteo(Map<NatureMesurePrevision, Map<Double, String>> meteo) {
        this.meteo = meteo;
    }

    public void addMeteo(NatureMesurePrevision nature, Map<Double, String> valeurUnite) {
        this.meteo.put(nature, valeurUnite);
    }
}
