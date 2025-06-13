package fr.diginamic.qualiair.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "mesure_population")
public class MesurePopulation extends Mesure {
    private int valeur;


    public MesurePopulation() {
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
}
