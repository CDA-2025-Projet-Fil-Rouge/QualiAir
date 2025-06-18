package fr.diginamic.qualiair.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "adresse")
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_adresse")
    private Long id;

    @Column(name = "numero_rue")
    private String numeroRue;
    @Column(name = "libelle_rue")
    private String libelleRue;

    @ManyToOne
    @JoinColumn(name = "id_commune")
    private Commune commune;

    @OneToMany(mappedBy = "adresse")
    private Set<Utilisateur> utilisateurs;

    public Adresse() {
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
     * @return numeroRue
     */
    public String getNumeroRue() {
        return numeroRue;
    }

    /**
     * Setter
     *
     * @param numeroRue sets value
     */
    public void setNumeroRue(String numeroRue) {
        this.numeroRue = numeroRue;
    }

    /**
     * Getter
     *
     * @return libelleRue
     */
    public String getLibelleRue() {
        return libelleRue;
    }

    /**
     * Setter
     *
     * @param libelleRue sets value
     */
    public void setLibelleRue(String libelleRue) {
        this.libelleRue = libelleRue;
    }

    /**
     * Getter
     *
     * @return commune
     */
    public Commune getCommune() {
        return commune;
    }

    /**
     * Setter
     *
     * @param commune sets value
     */
    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    /**
     * Getter
     *
     * @return utilisateurs
     */
    public Set<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    /**
     * Setter
     *
     * @param utilisateurs sets value
     */
    public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
}
