package fr.diginamic.qualiair.entity;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mesure_prevision",
        indexes = {
                @Index(name = "idx_mesure_prev_id_type", columnList = "id_mesure, type_releve")})
public class MesurePrevision extends Mesure {
    private String nature;
    private double valeur;
    private String unite;

    @Enumerated(EnumType.STRING)
    private TypeReleve typeReleve;

    public MesurePrevision() {
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
     * @return valeur
     */
    public double getValeur() {
        return valeur;
    }

    /**
     * Setter
     *
     * @param valeur sets value
     */
    public void setValeur(double valeur) {
        this.valeur = valeur;
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
     * @return typeReleve
     */
    public TypeReleve getTypeReleve() {
        return typeReleve;
    }

    /**
     * Setter
     *
     * @param typeReleve sets value
     */
    public void setTypeReleve(TypeReleve typeReleve) {
        this.typeReleve = typeReleve;
    }
}
