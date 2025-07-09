package fr.diginamic.qualiair.dto.historique;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class HistoriqueAirQuality {

    private String scope;
    private String code;
    private String codeElement;
    /**
     * DateReleve, Valeur
     */
    private Map<LocalDateTime, Double> historique = new HashMap<>();

    public HistoriqueAirQuality() {
    }

    public void addIndex(LocalDateTime dateReleve, double value) {
        this.historique.put(dateReleve, value);
    }

    /**
     * Getter
     *
     * @return codeElement
     */
    public String getCodeElement() {
        return codeElement;
    }

    /**
     * Setter
     *
     * @param codeElement sets value
     */
    public void setCodeElement(String codeElement) {
        this.codeElement = codeElement;
    }

    /**
     * Getter
     *
     * @return historique
     */
    public Map<LocalDateTime, Double> getHistorique() {
        return historique;
    }

    /**
     * Setter
     *
     * @param historique sets value
     */
    public void setHistorique(Map<LocalDateTime, Double> historique) {
        this.historique = historique;
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
