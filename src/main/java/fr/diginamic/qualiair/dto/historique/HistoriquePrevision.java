package fr.diginamic.qualiair.dto.historique;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class HistoriquePrevision {

    private String scope;
    private String code;
    private String nature;
    private String unite;
    /**
     * date:valeur
     */
    private Map<LocalDateTime, Double> valeurs = new HashMap<>();

    public HistoriquePrevision() {
    }


    public void addValeur(LocalDateTime date, double valeur) {
        this.valeurs.put(date, valeur);
    }

    /**
     * Getter
     *
     * @return nature
     */
    public String getNature() {
        return nature;
    }

    /**
     * Setter
     *
     * @param nature sets value
     */
    public void setNature(String nature) {
        this.nature = nature;
    }

    /**
     * Getter
     *
     * @return unite
     */
    public String getUnite() {
        return unite;
    }

    /**
     * Setter
     *
     * @param unite sets value
     */
    public void setUnite(String unite) {
        this.unite = unite;
    }

    /**
     * Getter
     *
     * @return valeurs
     */
    public Map<LocalDateTime, Double> getValeurs() {
        return valeurs;
    }

    /**
     * Setter
     *
     * @param valeurs sets value
     */
    public void setValeurs(Map<LocalDateTime, Double> valeurs) {
        this.valeurs = valeurs;
    }

    /**
     * Getter
     *
     * @return scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * Setter
     *
     * @param scope sets value
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Getter
     *
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter
     *
     * @param code sets value
     */
    public void setCode(String code) {
        this.code = code;
    }
}
