package fr.diginamic.qualiair.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "mesure_air")
public class MesureAir extends Mesure {
    private String codeElement;
    private int indice;
    private int valeur;
    private String unite;

    public MesureAir() {
    }

    /**
     * Getter
     *
     * @return indice
     */
    public int getIndice() {
        return indice;
    }

    /**
     * Setter
     *
     * @param indice sets value
     */
    public void setIndice(int indice) {
        this.indice = indice;
    }

    /**
     * Getter
     *
     * @return valeur
     */
    public int getValeur() {
        return valeur;
    }

    /**
     * Setter
     *
     * @param valeur sets value
     */
    public void setValeur(int valeur) {
        this.valeur = valeur;
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
}
