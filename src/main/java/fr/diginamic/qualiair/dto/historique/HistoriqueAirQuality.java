package fr.diginamic.qualiair.dto.historique;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class HistoriqueAirQuality {

    private String codeElement;
    /**
     * DateReleve, Valeur
     */
    private Map<LocalDateTime, Integer> historique = new HashMap<>();

    public HistoriqueAirQuality() {
    }

    public void addIndex(LocalDateTime dateReleve, int indice) {
        this.historique.put(dateReleve, indice);
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
    public Map<LocalDateTime, Integer> getHistorique() {
        return historique;
    }

    /**
     * Setter
     *
     * @param historique sets value
     */
    public void setHistorique(Map<LocalDateTime, Integer> historique) {
        this.historique = historique;
    }
}
