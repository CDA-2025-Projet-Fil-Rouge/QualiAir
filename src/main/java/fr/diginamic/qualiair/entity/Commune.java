package fr.diginamic.qualiair.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "commune")
public class Commune
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    @Column(name = "code_postal")
    private int codePostal;
    @Column(name = "code_insee")
    private int codeInsee;
    
    @ManyToOne
    @JoinColumn(name = "id_departement")
    private Departement departement;
    
    @ManyToOne
    @JoinColumn(name = "id_coordonnee")
    private Coordonnee coordonnee;
    
    @OneToMany(mappedBy = "commune")
    private Set<Adresse> adresse;
    
    public Commune()
    {
    }
    
    /**
     * Getter
     * @return id
     */
    public Long getId()
    {
        return id;
    }
    
    /**
     * Getter
     * @return nom
     */
    public String getNom()
    {
        return nom;
    }
    
    /**
     * Setter
     * @param nom sets value
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }
    
    /**
     * Getter
     * @return codePostal
     */
    public int getCodePostal()
    {
        return codePostal;
    }
    
    /**
     * Setter
     * @param codePostal sets value
     */
    public void setCodePostal(int codePostal)
    {
        this.codePostal = codePostal;
    }
    
    /**
     * Getter
     * @return codeInsee
     */
    public int getCodeInsee()
    {
        return codeInsee;
    }
    
    /**
     * Setter
     * @param codeInsee sets value
     */
    public void setCodeInsee(int codeInsee)
    {
        this.codeInsee = codeInsee;
    }
    
    /**
     * Getter
     * @return departement
     */
    public Departement getDepartement()
    {
        return departement;
    }
    
    /**
     * Setter
     * @param departement sets value
     */
    public void setDepartement(Departement departement)
    {
        this.departement = departement;
    }
    
    /**
     * Getter
     * @return coordonnee
     */
    public Coordonnee getCoordonnee()
    {
        return coordonnee;
    }
    
    /**
     * Setter
     * @param coordonnee sets value
     */
    public void setCoordonnee(Coordonnee coordonnee)
    {
        this.coordonnee = coordonnee;
    }
    
    /**
     * Getter
     * @return adresse
     */
    public Set<Adresse> getAdresse()
    {
        return adresse;
    }
    
    /**
     * Setter
     * @param adresse sets value
     */
    public void setAdresse(Set<Adresse> adresse)
    {
        this.adresse = adresse;
    }
}
