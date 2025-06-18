package fr.diginamic.qualiair.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "mesure_prevision_probabilite")
@Deprecated
public class MesurePrevisionProbabilite extends MesurePrevision {
    private double probabilite;

    public MesurePrevisionProbabilite() {
    }

    /**
     * Getter
     *
     * @return probabilite
     */
    public double getProbabilite() {
        return probabilite;
    }

    /**
     * Setter
     *
     * @param probabilite sets value
     */
    public void setProbabilite(double probabilite) {
        this.probabilite = probabilite;
    }
}
