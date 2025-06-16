package fr.diginamic.qualiair.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "commune")
public class Commune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commune")
    private Long id;

    private String nomPostal;

    private String nomComplet;
    @Column(name = "code_postal")
    private int codePostal;
    @Column(name = "code_insee")
    private String codeInsee;

    @ManyToOne
    @JoinColumn(name = "id_departement")
    private Departement departement;

    @OneToOne
    @JoinColumn(name = "id_coordonnee")
    private Coordonnee coordonnee;

    @OneToMany(mappedBy = "commune")
    private Set<Adresse> adresse;

    public Commune() {
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
     * @return nom
     */
    public String getNomComplet() {
        return nomComplet;
    }

    /**
     * Setter
     *
     * @param nom sets value
     */
    public void setNomComplet(String nom) {
        this.nomComplet = nom;
    }

    /**
     * Getter
     *
     * @return codePostal
     */
    public int getCodePostal() {
        return codePostal;
    }

    /**
     * Setter
     *
     * @param codePostal sets value
     */
    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    /**
     * Getter
     *
     * @return codeInsee
     */
    public String getCodeInsee() {
        return codeInsee;
    }

    /**
     * Setter
     *
     * @param codeInsee sets value
     */
    public void setCodeInsee(String codeInsee) {
        this.codeInsee = codeInsee;
    }

    /**
     * Getter
     *
     * @return departement
     */
    public Departement getDepartement() {
        return departement;
    }

    /**
     * Setter
     *
     * @param departement sets value
     */
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    /**
     * Getter
     *
     * @return coordonnee
     */
    public Coordonnee getCoordonnee() {
        return coordonnee;
    }

    /**
     * Setter
     *
     * @param coordonnee sets value
     */
    public void setCoordonnee(Coordonnee coordonnee) {
        this.coordonnee = coordonnee;
    }

    /**
     * Getter
     *
     * @return adresse
     */
    public Set<Adresse> getAdresse() {
        return adresse;
    }

    /**
     * Setter
     *
     * @param adresse sets value
     */
    public void setAdresse(Set<Adresse> adresse) {
        this.adresse = adresse;
    }

    /**
     * Getter
     *
     * @return libbelleAcheminenent
     */
    public String getNomPostal() {
        return nomPostal;
    }

    /**
     * Setter
     *
     * @param libbelleAcheminenent sets value
     */
    public void setNomPostal(String libbelleAcheminenent) {
        this.nomPostal = libbelleAcheminenent;
    }
}
