package fr.diginamic.qualiair.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "departement")
public class Departement
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private String code;
    
    @ManyToOne
    @JoinColumn(name = "id_region")
    private Region region;
    
    @OneToMany(mappedBy = "departement")
    private Set<Commune> commune;
    
    public Departement()
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
     * @return code
     */
    public String getCode()
    {
        return code;
    }
    
    /**
     * Setter
     * @param code sets value
     */
    public void setCode(String code)
    {
        this.code = code;
    }
    
    /**
     * Getter
     * @return region
     */
    public Region getRegion()
    {
        return region;
    }
    
    /**
     * Setter
     * @param region sets value
     */
    public void setRegion(Region region)
    {
        this.region = region;
    }
    
    /**
     * Getter
     * @return commune
     */
    public Set<Commune> getCommune()
    {
        return commune;
    }
    
    /**
     * Setter
     * @param commune sets value
     */
    public void setCommune(Set<Commune> commune)
    {
        this.commune = commune;
    }
}
