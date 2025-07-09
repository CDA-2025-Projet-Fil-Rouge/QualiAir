package fr.diginamic.qualiair.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "mesure_air")
public class MesureAir {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesure_air")
    private Long id;

    private String codeElement;
    private int indice;
    private double valeur;
    private String unite;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_mesure", referencedColumnName = "id_mesure")
    private Mesure mesure;

    public MesureAir() {
    }

    public MesureAir(String codeElement, double valeur, String unite) {
        super();
        this.codeElement = codeElement;
        this.valeur = valeur;
        this.unite = unite;
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
