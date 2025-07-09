package fr.diginamic.qualiair.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mesure_prevision")
public class MesurePrevision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesure_prev")
    private Long id;

    @Column(name = "date_prevision")
    private LocalDateTime datePrevision;

    private String nature;
    private double valeur;
    private String unite;

    @Enumerated(EnumType.STRING)
    private TypeReleve typeReleve;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_mesure", referencedColumnName = "id_mesure")
    private Mesure mesure;

    public MesurePrevision() {
    }

    /**
     * Getter
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter
     *
     * @return datePrevision
     */
    public LocalDateTime getDatePrevision() {
        return datePrevision;
    }

    /**
     * Setter
     *
     * @param datePrevision sets value
     */
    public void setDatePrevision(LocalDateTime datePrevision) {
        this.datePrevision = datePrevision;
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
     * @return mesure
     */
    public Mesure getMesure() {
        return mesure;
    }

    /**
     * Setter
     *
     * @param mesure sets value
     */
    public void setMesure(Mesure mesure) {
        this.mesure = mesure;
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
