package fr.diginamic.qualiair.dto.historique;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class HistoriquePopulation {

    private String scope;
    private String code;
    private String nom;

    private Map<LocalDateTime, Integer> historique = new HashMap<>();

    public HistoriquePopulation() {
    }

    public void addIndex(LocalDateTime dateReleve, int population) {
        this.historique.put(dateReleve, population);
    }

    /**
     * Getter
     *
     * @return nomVille
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter
     *
     * @param nom sets value
     */
    public void setNom(String nom) {
        this.nom = nom;
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
