package fr.diginamic.qualiair.dto.favoris;

import fr.diginamic.qualiair.dto.carte.InfoCarteCommune;

import java.util.HashMap;
import java.util.Map;

public class InfoFavorite {
    private Map<Long, Long> favId = new HashMap<>();
    private InfoCarteCommune informations;

    public InfoFavorite() {
    }

    /**
     * Getter
     *
     * @return favId
     */
    public Map<Long, Long> getFavId() {
        return favId;
    }

    /**
     * Setter
     *
     * @param favId sets value
     */
    public void setFavId(Map<Long, Long> favId) {
        this.favId = favId;
    }

    public void setFavID(Long userId, Long communeId) {
        favId.put(userId, communeId);
    }

    /**
     * Getter
     *
     * @return informations
     */
    public InfoCarteCommune getInformations() {
        return informations;
    }

    /**
     * Setter
     *
     * @param informations sets value
     */
    public void setInformations(InfoCarteCommune informations) {
        this.informations = informations;
    }
}
