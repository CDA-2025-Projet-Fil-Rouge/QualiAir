package fr.diginamic.qualiair.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mesure_population")
public class MesurePopulation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesure_pop")
    private Long id;

    private int valeur;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_mesure", referencedColumnName = "id_mesure")
    private Mesure mesure;

    public MesurePopulation() {
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
}
