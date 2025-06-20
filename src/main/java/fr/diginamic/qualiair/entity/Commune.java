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

    private String nomSimple;
    private String nomReel;
    @Column(name = "code_postal")
    private String codePostal;
    @Column(name = "code_insee")
    private String codeInsee;

    @ManyToOne
    @JoinColumn(name = "id_departement")
    private Departement departement;

    @OneToOne
    @JoinColumn(name = "id_coordonnee_principale")
    private Coordonnee coordonnee;

    @OneToMany(mappedBy = "commune")
    private Set<Adresse> adresse;

    @ManyToMany(mappedBy = "favCommunes")
    private Set<Utilisateur> utilisateurs;

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
     * @return nomSimple
     */
    public String getNomSimple() {
        return nomSimple;
    }

    /**
     * Setter
     *
     * @param nomSimple sets value
     */
    public void setNomSimple(String nomSimple) {
        this.nomSimple = nomSimple;
    }

    /**
     * Getter
     *
     * @return nomReel
     */
    public String getNomReel() {
        return nomReel;
    }

    /**
     * Setter
     *
     * @param nomReel sets value
     */
    public void setNomReel(String nomReel) {
        this.nomReel = nomReel;
    }

    /**
     * Getter
     *
     * @return codePostal
     */
    public String getCodePostal() {
        return codePostal;
    }

    /**
     * Setter
     *
     * @param codePostal sets value
     */
    public void setCodePostal(String codePostal) {
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
