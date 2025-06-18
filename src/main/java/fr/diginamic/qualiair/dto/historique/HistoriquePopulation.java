package fr.diginamic.qualiair.dto.historique;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class HistoriquePopulation {

    private String nomVille;

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
    public String getNomVille() {
        return nomVille;
    }

    /**
     * Setter
     *
     * @param nomVille sets value
     */
    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
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
