package fr.diginamic.qualiair.dto.historique;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class HistoriquePopulation {

    private String scope;
    private String code;
    private String nom;

    private Map<LocalDateTime, Double> historique = new HashMap<>();

    public HistoriquePopulation() {
    }

    public void addIndex(LocalDateTime dateReleve, Double population) {
        this.historique.put(dateReleve, population);
    }

    public void addIndex(LocalDateTime date, int valeur) {
        this.historique.put(date, (double) valeur);
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
